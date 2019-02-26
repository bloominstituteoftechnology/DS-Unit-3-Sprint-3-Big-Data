#!/bin/usr/env python 3

'''
This module is the beginning of Twitter analytics in the cloud for johnpharmd
'''

from env_vars import check_password
import twitter
# note that, for SageMaker, will need to import python 3, numpy, pandas, dask,
# numba, python-twitter


def start_api():
    api_dict = check_password()
    api = twitter.Api(consumer_key=api_dict['api_key'],
                  consumer_secret = api_dict['api_secret_key'],
                  access_token_key = api_dict['token'],
                  access_token_secret = api_dict['token_secret'])

    # print(api.VerifyCredentials())
    print('Access to Twitter API is now initiated.')
    return api


def search_tweets_for():
    '''
    This function searches tweets for given string object
    '''
    api = start_api()
    string = input('Enter string to search for in tweets: ')
    search = api.GetSearch(string)
    for tweet in search:
        print(tweet.text)


if __name__ == '__main__':
    # start_api()
    search_tweets_for()

