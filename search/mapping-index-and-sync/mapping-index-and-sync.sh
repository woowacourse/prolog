#!/bin/sh
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

curl -v https://dev-api.prolog.techcourse.co.kr/sync
