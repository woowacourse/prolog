#!/bin/bash

DEV_API="https://dev-api.prolog.techcourse.co.kr"
PROD_API="https://api.prolog.techcourse.co.kr"
USE_API=""

if [ $USER == "ELASTIC-STACK-DEV" ];then
	USE_API=${DEV_API}
else
	USE_API=${PROD_API}
fi

curl -X PUT "localhost:9200/studylog-document?pretty" -H 'Content-Type: application/json' -d'
{
  "settings":{
      "index":{
        "analysis":{
          "tokenizer":{
            "nori_tokenizer_mixed_dict":{
              "type":"nori_tokenizer",
              "decompound_mode":"mixed"
            }
          },
          "analyzer": {
            "korean":{
              "type":"custom",
              "tokenizer":"nori_tokenizer_mixed_dict",
              "filter":[
                "nori_readingform","lowercase",
                "nori_part_of_speech_basic"]
              }
            },
            "filter":{
              "nori_part_of_speech_basic":{
                "type":"nori_part_of_speech",
                "stoptags":["E","IC","J","MAG","MAJ","MM","SP","SSC","SSO","SC","SE","XPN","XSA","XSN","XSV","UNA","NA","VSV"]
          }
        }
      }
    }
  }
}
'

curl -v ${USE_API}/sync
