from aws_cdk import (
    aws_ec2 as ec2,
    aws_ecs as ecs,
    aws_ecs_patterns as ecs_patterns,
    core 
)

class NetworkStack(core.Stack):
    def __init__(self, scope: core.Construct, deploy_env, **kwargs) -> None:
        self.deploy_env = deploy_env
        self.rds_port = 5432
        super().__init__(scope, id=f"{self.deploy_env}-network-stack", **kwargs)

        self.custom_vpc = ec2.Vpc(self, f"vpc-{self.deploy_env}")