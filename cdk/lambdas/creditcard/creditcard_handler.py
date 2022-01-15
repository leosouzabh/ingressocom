import random
import logging
import time
import json 
import random # define the random module  
import string    


logger = logging.getLogger("lambda")
logging.basicConfig(level = logging.INFO, force=True)


def handler(event, context):
    logging.info(json.dumps(event))
    bodyPost = json.loads(event["body"])
    logging.info(json.dumps(bodyPost))
    
    card_number = bodyPost["cardNumber"]
    logging.info(f"INIT: xxxx.xxxx.xxxx.{card_number[-4:]}")
    sleep_time = random.randint(1,5)
    time.sleep(sleep_time)
    logging.info(f"END: xxxx.xxxx.xxxx.{card_number[-4:]}")
    
    hash = ''.join(random.choices(string.ascii_uppercase + string.digits, k = 10))
    return {
        'statusCode': 200,
        'body': json.dumps(
            {
                'hash': str(hash)
            }    
        )

    }
