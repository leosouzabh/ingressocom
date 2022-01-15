from aws_cdk import (
    core as cdk,
    aws_s3_assets as s3_assets,
    aws_elasticbeanstalk as elasticbeanstalk,
    aws_iam as iam
)
from stacks.RdsStack import RdsStack
from os import path

class WebappStack(cdk.Stack):


    def __init__(self, scope: cdk.Construct, deploy_env: str, payment_service_url: str, 
        booking_service_host: str, rds_stack: RdsStack, **kwargs) -> None:

        self.deploy_env = deploy_env
        self.app_name = f"{deploy_env}-portal"
        super().__init__(scope, f"{self.app_name}-webapp", **kwargs)


        asset = s3_assets.Asset(self, f"{self.app_name}_asset_jar",
            path=path.join("assets/portal-0.0.1-SNAPSHOT.jar")
        )

        app = elasticbeanstalk.CfnApplication(self, 'Application',
            application_name= self.app_name,
        )

        app_version_props = elasticbeanstalk.CfnApplicationVersion(self, f'{self.deploy_env}-AppVersion', 
            application_name=app.application_name,
            source_bundle=elasticbeanstalk.CfnApplicationVersion.SourceBundleProperty(
                s3_bucket=asset.s3_bucket_name,
                s3_key=asset.s3_object_key
            ),
            # the properties below are optional
            description="description"
        )

        app_version_props.add_depends_on(app)


        #Create role and instance profile
        myRole = iam.Role(self, f"{self.app_name}-aws-elasticbeanstalk-ec2-role", 
            assumed_by = iam.ServicePrincipal('ec2.amazonaws.com'),
        )
    
        myRole.add_managed_policy(
            iam.ManagedPolicy.from_aws_managed_policy_name('AWSElasticBeanstalkWebTier')
        )

        my_profile_name = f"{self.app_name}-InstanceProfile"

        instanceProfile = iam.CfnInstanceProfile(self, my_profile_name,
            instance_profile_name = my_profile_name,
            roles = [
                myRole.role_name
            ]
        )

        elb_env = elasticbeanstalk.CfnEnvironment(self, f'{self.deploy_env}-Environment', 
            environment_name= f'{self.app_name}-environment',
            application_name= app.application_name,
            solution_stack_name= '64bit Amazon Linux 2 v3.2.9 running Corretto 8',
            option_settings = [
                elasticbeanstalk.CfnEnvironment.OptionSettingProperty(
                    namespace="aws:autoscaling:launchconfiguration",
                    option_name="IamInstanceProfile",
                    value=my_profile_name,
                ),
                elasticbeanstalk.CfnEnvironment.OptionSettingProperty(
                    namespace="aws:autoscaling:asg",
                    option_name="MinSize",
                    value="1",
                ),
                elasticbeanstalk.CfnEnvironment.OptionSettingProperty(
                    namespace="aws:autoscaling:asg",
                    option_name="MaxSize",
                    value="1",
                ),
                elasticbeanstalk.CfnEnvironment.OptionSettingProperty(
                    namespace="aws:ec2:instances",
                    option_name="InstanceTypes",
                    value="t2.micro",
                ),
                self.get_env_var_option("SERVER_PORT", "5000"),
                self.get_env_var_option("PAYMENT_GATEWAY_ENDPOINT", payment_service_url),
                self.get_env_var_option("EXTERNAL_BOOKING_ENDPOINT", f"http://{booking_service_host}/booking"),

                self.get_env_var_option("SPRING_DATASOURCE_URL", rds_stack.jdbc_url),
                self.get_env_var_option("SPRING_DATASOURCE_USERNAME", rds_stack.secret_username),
                self.get_env_var_option("SPRING_DATASOURCE_PASSWORD", rds_stack.secret_password)
            ],
            version_label= app_version_props.ref,
        )

    def get_env_var_option(self, environment_name: str, environment_value: str) -> elasticbeanstalk.CfnEnvironment.OptionSettingProperty:

        return elasticbeanstalk.CfnEnvironment.OptionSettingProperty(
            namespace="aws:elasticbeanstalk:application:environment",
            option_name=environment_name,
            value=environment_value
        )        

