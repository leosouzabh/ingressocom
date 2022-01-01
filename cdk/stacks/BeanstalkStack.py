from aws_cdk import (
    core as cdk,
    aws_s3_assets as s3_assets,
    aws_elasticbeanstalk as elasticbeanstalk,
    aws_iam as iam
)

from os import path

class BeanstalkStack(cdk.Stack):

    def __init__(self, scope: cdk.Construct, construct_id: str, payment_service_url: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        app_name = "portal"

        asset = s3_assets.Asset(self, f"{app_name}_asset_jar",
            path=path.join("assets/portal-0.0.1-SNAPSHOT.jar")
        )

        app = elasticbeanstalk.CfnApplication(self, 'Application',
            application_name= app_name,
        );

        app_version_props = elasticbeanstalk.CfnApplicationVersion(self, 'AppVersion', 
            application_name=app.application_name,
            source_bundle=elasticbeanstalk.CfnApplicationVersion.SourceBundleProperty(
                s3_bucket=asset.s3_bucket_name,
                s3_key=asset.s3_object_key
            ),
            # the properties below are optional
            description="description"
        );

        app_version_props.add_depends_on(app)


        #Create role and instance profile
        myRole = iam.Role(self, f"{app_name}-aws-elasticbeanstalk-ec2-role", 
            assumed_by = iam.ServicePrincipal('ec2.amazonaws.com'),
        );

        
        
        myRole.add_managed_policy(
            iam.ManagedPolicy.from_aws_managed_policy_name('AWSElasticBeanstalkWebTier')
        );

        my_profile_name = f"{app_name}-InstanceProfile"

        instanceProfile = iam.CfnInstanceProfile(self, my_profile_name,
            instance_profile_name = my_profile_name,
            roles = [
                myRole.role_name
            ]
        );

        elb_env = elasticbeanstalk.CfnEnvironment(self, 'Environment', 
            environment_name= 'MyWebAppEnvironment',
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
                elasticbeanstalk.CfnEnvironment.OptionSettingProperty(
                    namespace="aws:elasticbeanstalk:application:environment",
                    option_name="SERVER_PORT",
                    value="5000",
                ),
                elasticbeanstalk.CfnEnvironment.OptionSettingProperty(
                    namespace="aws:elasticbeanstalk:application:environment",
                    option_name="PAYMENT_SERVICE_URL",
                    value=payment_service_url,
                )
            ],
            version_label= app_version_props.ref,
        );


