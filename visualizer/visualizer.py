from cProfile import label
import pymongo
import pandas as pd
import dateutil.parser
from pymongo import MongoClient
import matplotlib
import matplotlib.pyplot as plt


##### Util functions
def return_success_match(row):
    if row.requestStatus == "SUCCESS":
        return 1
    return 0

def return_timeout_match(row):
    if row.requestStatus == "TIME_OUT":
        return 1
    return 0

def return_generated_time(row):
    date_object = dateutil.parser.isoparse(row.endTime)
    return date_object.strftime("%m/%d/%Y, %H:%M:%S")

## constants
SNAPSHOT_Id=1651622318

## Mongo connection
client = MongoClient('localhost', 27017)
mydb = client["stress-test"]

## query the records and insert them into pandas data frames
data = pd.DataFrame(list(mydb["request-log"].find({"snapshotId": SNAPSHOT_Id})))
data['is_success'] = data.apply(lambda row: return_success_match(row), axis=1)
data['is_timeout'] = data.apply(lambda row: return_timeout_match(row), axis=1)
data['time'] = data.apply(lambda row: return_generated_time(row), axis=1)

df2 = data.groupby('time', as_index=False)['is_success'].sum()
df3 = data.groupby('time', as_index=False)['is_timeout'].sum()
merged = pd.merge(df2, df3, on='time')

## plot timeouts and success responses.
figure, axis = plt.subplots(2)

axis[0].plot(list(df2.time), list(df2.is_success), color='#70ff44')
axis[0].set_title("Successful Request Processes")

axis[1].plot(list(df3.time), list(df3.is_timeout), color='#FF4455')
axis[1].set_title("Timedout Request Processes")

plt.show()