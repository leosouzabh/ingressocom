from aws_cdk import (
    core as cdk,
    aws_apigateway as apigateway,
    aws_lambda as _lambda,
    aws_ssm as ssm
)

class ApiGatewayCreditCardStack(cdk.Stack):

    def __init__(self, scope: cdk.Construct, construct_id: str, lambda_handler: _lambda.Function, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        api = apigateway.RestApi(self, "creditcard-api3",
                  rest_api_name="Payment Service",
                  description="This service serves widgets.")

        get_widgets_integration = apigateway.LambdaIntegration(lambda_handler,
                request_templates={"application/json": '{ "statusCode": "200" }'})

        api.root.add_method("POST", get_widgets_integration)  
        
        self.api_url = f"https://{api.rest_api_id}.execute-api.{self.region}.amazonaws.com/prod"
        
        ssm.StringParameter(self, "Parameter",
            allowed_pattern=".*",
            description="The value Foo",
            parameter_name="FooParameter",
            string_value=self.api_url,
            tier=ssm.ParameterTier.ADVANCED
        )

