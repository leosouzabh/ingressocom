from aws_cdk import (
    aws_rds as rds,
    aws_ec2 as ec2,
    core 
)

class RdsStack(core.Stack):
    def __init__(self, scope: core.Construct, deploy_env, custom_vpc, **kwargs) -> None:
        self.deploy_env = deploy_env
        self.custom_vpc = custom_vpc
        self.rds_port = 5432
        super().__init__(scope, id=f"{self.deploy_env}-rds-stack", **kwargs)

        self.ticket_rds_sg = ec2.SecurityGroup(
            self,  f"{self.deploy_env}-ticket-sg",
            vpc=self.custom_vpc,
            allow_all_outbound=True,
            security_group_name=f"{self.deploy_env}-ticket-sg"
        )

        #Opening the Security Group to everyone
        self.ticket_rds_sg.add_ingress_rule(
            peer=ec2.Peer.ipv4("0.0.0.0/0"), connection=ec2.Port.tcp(self.rds_port)
        )

        #Opening the RDS to all from the private subnet
        for subnet in self.custom_vpc.private_subnets:
            self.ticket_rds_sg.add_ingress_rule(
                peer=ec2.Peer.ipv4(subnet.ipv4_cidr_block), connection=ec2.Port.tcp(self.rds_port)
            )

        self.ticket_rds_parameter_group = rds.ParameterGroup(
            self, f"{self.deploy_env}-rds-parameter-group",
            description="Parameter group to allow CDC from using RDS using DMS",
            engine=rds.DatabaseInstanceEngine.postgres(
                version=rds.PostgresEngineVersion.VER_12_4
            ),
            parameters={"rds.logical_replication": "1", "wal_sender_timeout": "0"},
        )

        self.ticket_rds = rds.DatabaseInstance(
            self, f"{self.deploy_env}-ticket-rds",
            engine=rds.DatabaseInstanceEngine.postgres(
                version=rds.PostgresEngineVersion.VER_12_4
            ),
            database_name="ticketdb",
            instance_type=ec2.InstanceType("t3.micro"),
            vpc=self.custom_vpc,
            instance_identifier=f"{self.deploy_env}-rds-ticket-db",
            port=self.rds_port,
            vpc_subnets=ec2.SubnetSelection(subnet_type=ec2.SubnetType.PUBLIC),
            subnet_group=rds.SubnetGroup(
                self,
                f"{self.deploy_env}-rds-subnet",
                description="place RDS on public subnet",
                vpc=self.custom_vpc,
                vpc_subnets=ec2.SubnetSelection(subnet_type=ec2.SubnetType.PUBLIC),
            ),
            parameter_group=self.ticket_rds_parameter_group,
            security_groups=[self.ticket_rds_sg],
            removal_policy=core.RemovalPolicy.DESTROY,
            **kwargs,
        )
    
    @property
    def jdbc_url(self) -> str:
        return f"jdbc:postgresql://{self.ticket_rds.db_instance_endpoint_address}:{self.rds_port}/{self.secret_db_name}"

    @property
    def secret_username(self) -> str:
        return core.CfnDynamicReference(
            core.CfnDynamicReferenceService.SECRETS_MANAGER,
            key=f"{self.ticket_rds.secret.secret_arn}:SecretString:username",
        ).to_string()

    @property
    def secret_db_name(self) -> str:
        return core.CfnDynamicReference(
            core.CfnDynamicReferenceService.SECRETS_MANAGER,
            key=f"{self.ticket_rds.secret.secret_arn}:SecretString:dbname",
        ).to_string()

    @property
    def secret_password(self) -> str:
        return core.CfnDynamicReference(
            core.CfnDynamicReferenceService.SECRETS_MANAGER,
            key=f"{self.ticket_rds.secret.secret_arn}:SecretString:password",
        ).to_string()
        
