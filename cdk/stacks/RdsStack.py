from aws_cdk import (
    aws_rds as rds,
    aws_ec2 as ec2,
    core 
)

class RdsStack(core.Stack):
    def __init__(self, scope: core.Construct, deploy_env, **kwargs) -> None:
        self.deploy_env = deploy_env
        self.rds_port = 5432
        super().__init__(scope, id=f"{self.deploy_env}-network-stack", **kwargs)

        self.custom_vpc = ec2.Vpc(self, f"vpc-{self.deploy_env}")

        self.orders_rds_sg = ec2.SecurityGroup(
            self,  f"orders-{self.deploy_env}-sg",
            vpc=self.custom_vpc,
            allow_all_outbound=True,
            security_group_name=f"orders-{self.deploy_env}-sg"
        )

        #Opening the Security Group to everyone
        self.orders_rds_sg.add_ingress_rule(
            peer=ec2.Peer.ipv4("0.0.0.0/0"), connection=ec2.Port.tcp(self.rds_port)
        )

        #Opening the RDS to all from the private subnet
        for subnet in self.custom_vpc.private_subnets:
            self.orders_rds_sg.add_ingress_rule(
                peer=ec2.Peer.ipv4(subnet.ipv4_cidr_block), connection=ec2.Port.tcp(self.rds_port)
            )

        self.orders_rds_parameter_group = rds.ParameterGroup(
            self, f"{self.deploy_env}-rds-parameter-group",
            description="Parameter group to allow CDC from using RDS using DMS",
            engine=rds.DatabaseInstanceEngine.postgres(
                version=rds.PostgresEngineVersion.VER_12_4
            ),
            parameters={"rds.logical_replication": "1", "wal_sender_timeout": "0"},
        )

        self.orders_rds = rds.DatabaseInstance(
            self, f"orders-{self.deploy_env}-rds",
            engine=rds.DatabaseInstanceEngine.postgres(
                version=rds.PostgresEngineVersion.VER_12_4
            ),
            database_name="orders",
            instance_type=ec2.InstanceType("t3.micro"),
            vpc=self.custom_vpc,
            instance_identifier=f"rds-{self.deploy_env}-orders-db",
            port=self.rds_port,
            vpc_subnets=ec2.SubnetSelection(subnet_type=ec2.SubnetType.PUBLIC),
            subnet_group=rds.SubnetGroup(
                self,
                f"rds-{self.deploy_env}-subnet",
                description="place RDS on public subnet",
                vpc=self.custom_vpc,
                vpc_subnets=ec2.SubnetSelection(subnet_type=ec2.SubnetType.PUBLIC),
            ),
            parameter_group=self.orders_rds_parameter_group,
            security_groups=[self.orders_rds_sg],
            removal_policy=core.RemovalPolicy.DESTROY,
            **kwargs,
        )


