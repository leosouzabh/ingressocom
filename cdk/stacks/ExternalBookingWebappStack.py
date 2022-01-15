from aws_cdk import (
    aws_ec2 as ec2,
    aws_ecs as ecs,
    aws_ecs_patterns as ecs_patterns,
    core 
)
from os import path

class ExternalBookingWebappStack(core.Stack):
    def __init__(self, scope: core.Construct, deploy_env, vpc: ec2.Vpc, **kwargs) -> None:
        self.deploy_env = deploy_env
        super().__init__(scope, id=f"{self.deploy_env}-ext-booking-service", **kwargs)
        #super().__init__(scope, id=f"{deploy_env}-external-booking-service", **kwargs)
        cluster = ecs.Cluster(self, f"{deploy_env}-ext-booking-cluster", vpc=vpc)

        self.service = ecs_patterns.ApplicationLoadBalancedFargateService(self, f"{deploy_env}-ext-booking-fargate-service",
            cluster=cluster,            # Required
            cpu=512,                    # Default is 256
            desired_count=6,            # Default is 1
            task_image_options=ecs_patterns.ApplicationLoadBalancedTaskImageOptions(
                image=ecs.ContainerImage.from_asset(path.join("assets/booking-system"))
            ),
            memory_limit_mib=2048,      # Default is 512
            public_load_balancer=True)  # Default is False
        
        self.booking_service_host = self.service.load_balancer.load_balancer_dns_name
        