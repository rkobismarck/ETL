#!/bin/bash
echo "Started ETL Process."
pwd=$(pwd)
date=$(date +'%d%m%Y')
time=$(date +'%H:%M:%S')
echo "Actual directory: $pwd"
spark-submit --class com.fexco.poc.ETL --master local[2]  --verbose  --conf "spark.driver.extraJavaOptions=-Dlog4j.configuration=file://////$pwd/ETLEngine/Log4j.properties -DMY_DATE=$date -DMY_TIME=$time" ETLEngine/aggregationengine_2.11-0.1.jar ETLData/Input/raw-data.csv csv "ETLDataOutput/$date/Scoring" csv scoring 100
echo "Finished ETL Process."