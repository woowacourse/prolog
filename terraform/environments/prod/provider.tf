terraform {
    required_providers {
        aws = {
            source  = "hashicorp/aws"
            version = "5.54.1"
        }
    }
}

provider "aws" {
    region = var.region

    /** Note:
    AWS_ACCESS_KEY_ID와 AWS_SECRET_ACCESS_KEY는 환경변수를 통해 설정해야 합니다.
    아래와 같이 환경 변수를 설정하세요:

    export AWS_ACCESS_KEY_ID="your-access-key"
    export AWS_SECRET_ACCESS_KEY="your-secret-key"

    GitHub Actions에서는 환경 변수를 Secrets에 저장하여 사용해야 합니다.
    */
}
