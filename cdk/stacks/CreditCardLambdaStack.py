from aws_cdk import (
    core as cdk,
    aws_lambda as _lambda
)

from os import path

class CreditCardLambdaStack(cdk.Stack):

    def __init__(self, scope: cdk.Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        self.handler = _lambda.Function(
            scope=self,
            id="creditcardLambda",
            runtime=_lambda.Runtime.PYTHON_3_9,
            code=_lambda.Code.from_asset(path.join("lambdas/creditcard")),
            timeout=cdk.Duration.seconds(amount=30),
            handler="creditcard_handler.handler"
        )