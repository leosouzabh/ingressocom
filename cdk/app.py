#!/usr/bin/env python3
from aws_cdk import core
from stacks.WebappStack import WebappStack
from stacks.CreditCardLambdaStack import CreditCardLambdaStack
from stacks.NetworkStack import NetworkStack
from stacks.ExternalBookingWebappStack import ExternalBookingWebappStack
from stacks.RdsStack import RdsStack
import os


app = core.App()

deploy_env = os.getenv('ENV') if os.getenv('ENV') else 'dev'

network_stack = NetworkStack(
    app,
    deploy_env)

rds_stack = RdsStack(
    app,
    deploy_env,
    network_stack.custom_vpc)

booking_service_stack = ExternalBookingWebappStack (
    app,
    deploy_env,
    network_stack.custom_vpc)

creditcard_stack = CreditCardLambdaStack(
    app,
    deploy_env)

beanstalk_stack = WebappStack(
    app, 
    deploy_env,
    creditcard_stack.api_url,
    booking_service_stack.booking_service_host,
    rds_stack)

app.synth()
