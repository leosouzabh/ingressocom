from aws_cdk import (
    core as cdk,
    aws_lambda as _lambda,
    aws_apigateway as apigateway

)

from os import path

class CreditCardLambdaStack(cdk.Stack):

    def __init__(self, scope: cdk.Construct, deploy_env: str, **kwargs) -> None:
        self.deploy_env = deploy_env
        super().__init__(scope, id=f"{self.deploy_env}-creditcard-lambda", **kwargs)
        
        handler = _lambda.Function(
            scope=self,
            id="creditcardLambda",
            runtime=_lambda.Runtime.PYTHON_3_9,
            code=_lambda.Code.from_asset(path.join("lambdas/creditcard")),
            timeout=cdk.Duration.seconds(amount=30),
            handler="creditcard_handler.handler"
        )


        api = apigateway.RestApi(self, "creditcard-api",
            rest_api_name="Payment Service",
            description="This service serves widgets.")

        get_widgets_integration = apigateway.LambdaIntegration(handler,
            request_templates={"application/json": '{ "statusCode": "200" }'})

        api.root.add_method("POST", get_widgets_integration)  
        
        self.api_url = f"https://{api.rest_api_id}.execute-api.{self.region}.amazonaws.com/prod"