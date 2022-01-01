#!/usr/bin/env python3
from aws_cdk import core
from stacks.ApiGatewayCreditCardStack import ApiGatewayCreditCardStack
from stacks.BeanstalkStack import BeanstalkStack
from stacks.CreditCardLambdaStack import CreditCardLambdaStack

app = core.App()
creditcard_stack = CreditCardLambdaStack(app, "CreditCardLambdaStack")

api_creditcard_stack = ApiGatewayCreditCardStack(app, "ApiGatewayCreditCardStack", creditcard_stack.handler)
beanstalk_stack = BeanstalkStack(app, "BeanstalkStack", api_creditcard_stack.api_url)

app.synth()
