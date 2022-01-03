import random
import logging
import time

logger = logging.getLogger("lambda")
logging.basicConfig(level = logging.INFO, force=True)


def handler(event, context):
    card_number = event["cardNumber"]
    logging.info(f"INIT: xxxx.xxxx.xxxx.{card_number[-4:]}")
    sleep_time = random.randint(1,5)
    time.sleep(sleep_time)
    logging.info(f"END: xxxx.xxxx.xxxx.{card_number[-4:]}")
