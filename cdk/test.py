import os
env = os.getenv('ENV') if os.getenv('ENV') else 'dev'
print(env)