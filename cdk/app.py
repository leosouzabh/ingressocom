#!/usr/bin/env python3
from aws_cdk import core
from stacks.ApiGatewayCreditCardStack import ApiGatewayCreditCardStack
from stacks.CreditCardLambdaStack import CreditCardLambdaStack

app = core.App()
creditcard_stack = CreditCardLambdaStack(app, "CreditCardLambdaStack")

api_creditcard_stack = ApiGatewayCreditCardStack(app, "ApiGatewayCreditCardStack", creditcard_stack.handler)
print(f">>>>>>>>>>>>>>>>>>> {api_creditcard_stack.api_url}")

app.synth()
