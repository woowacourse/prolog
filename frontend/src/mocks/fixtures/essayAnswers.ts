import { EssayAnswer } from '../../models/EssayAnswers';

type EssayAnswersFilterParams = {
  curriculumId: number;
  keywordId?: number;
  quizIds?: number[];
  memberIds?: number[];
  page?: number;
  size?: number;
};

const data: Array<{
  curriculumId: number;
  essayAnswers: (EssayAnswer & {
    keywordId: number;
  })[];
}> = [
  {
    curriculumId: 1,
    essayAnswers: [
      {
        id: 71,
        keywordId: 1,
        quiz: {
          quizId: 29,
          isLearning: true,
          question: 'Varargs는 어떻게 사용하고, 사용했을 때 장점은 무엇인가요?',
        },
        answer:
          '# Varargs\n***\nVarargs(가변인자)는 JDK 5에서 새로 도입된 기능이다.\nparameter의 수를 가변적으로 조절할 수 있게 해주는 기능이다.\n<br>\n\n## 사용법\n***\nparameter를 여러개 정의하고 싶은 method를 선언할 때, parameter 부분에 ...을 붙이면 된다.\n\n```java\npublic int calculateSum(int... numbers) {\n    return Arrays.stream(numbers).sum();\n}\n```\n\n위 코드에서 유추할 수 있듯이, varargs는 내부적으로 array를 반환한다.\ncompile 시에 Object array가 만들어지고, varargs에 할당된 인자들이 array의 원소로 들어간다.\n<br>\n\n## 장점\n***\n만약 위의 method에서, varargs를 사용하지 않는다고 가정해보자.\n그렇다면, parameter가 2개, 3개, 4개, ..., 인 calculateSum() method를 전부 overloading해야 할 것이다.\n\n```java\npublic int getSum(int number1, int number2) {\n\treturn number1 + number2;\n}\npublic int getSum(int number1, int number2, int number3) {\n\treturn number1 + number2 + number3;\n}\n\n...\n\n\n```\n\nvarargs를 사용하면 이러한 overloading을 하지 않도록 할 수 있다.',
        author: {
          id: 304,
          username: 'Jaeyoung22',
          nickname: 'ReO',
          role: 'CREW',
          imageUrl: 'https://avatars.githubusercontent.com/u/89302528?v=4',
        },
        createdAt: '2023-04-09T17:56:25.642891',
        updatedAt: '2023-04-09T17:56:25.642891',
      },
      {
        id: 69,
        keywordId: 2,
        quiz: {
          quizId: 29,
          isLearning: false,
          question: 'Varargs는 어떻게 사용하고, 사용했을 때 장점은 무엇인가요?',
        },
        answer:
          '# Varargs 탄생 배경\n\nJDK 1.4 전까지는 다양한 수의 인자를 가진 메서드를 선언할 수 없었습니다.\n이를 해결하기 위해선 두 가지 방식을 사용했습니다.\n1. 메서드 오버로딩\n이 방법은 코드의 길이를 무한대로 증가시킵니다.\n2. 전달할 값들을 배열로 받기\n\n결국, Varargs는 **다양한 범위의 인자를 받기 위한 해결책**으로 탄생하게 되었습니다.\n\n# Varargs 사용법 및 특징\n\n```java\npublic class VarargsPractice {\n    public static void main(String[] args) {\n        printValues(); // 특징 1. 0개 이상의 인자를 전달할 수 있습니다.\n        printValues("1");\n        printValues("1", "2");\n        printValues("1", "2", "3");\n        printValues(new String[]{"1", "2", "3"}); // 특징 2. 실제로는 배열로 동작합니다.\n    }\n\n    // 사용법: 파라미터에 타입과 ... 을 함께 작성합니다. (String...)\n    public static void printValues(final String... values) {\n        for (int i = 0; i < values.length; i++) {\n            System.out.println(values[i]); // 배열로 동작하기에 인덱스로 접근 가능합니다.\n        }\n    }\n}\n```\n\n# 잘못된 Varargs 사용법\n\n1. 한 메서드에 2가지 타입의 가변인자(varargs)는 사용하지 못합니다.\n```java\nvoid method(String... inputs, int... numbers);\n```\n\n2. 가변인자를 첫 번째 파라미터로 선언하지 못합니다.\n```java\nvoid method(int... numbers, String input);\n```\n\n# Varargs의 장점\n\n1. 메서드 인자의 수를 동적으로 결정 및 처리할 수 있습니다.\n2. 코드를 간결하게 유지할 수 있습니다.\n\n# Varargs의 단점\n\n1. 공간 복잡도가 증가합니다.\n가변 인자는 곧, 배열입니다.\n가변 인자를 전달받은 메서드는 매번 새로운 배열을 생성해야 합니다.\n\n# Varargs를 사용하는 기준\n(개인적인 기준입니다.)\n\n1. 인자의 개수를 다양하게 받아야 하는 상황이다.\n2. 성능상 그다지 중요하지 않은 상황이다.',
        author: {
          id: 287,
          username: 'kdkdhoho',
          nickname: '도기\uD83D\uDC36',
          role: 'CREW',
          imageUrl: 'https://avatars.githubusercontent.com/u/66300965?v=4',
        },
        createdAt: '2023-04-08T23:54:48.667266',
        updatedAt: '2023-04-08T23:54:48.667266',
      },
    ],
  },
  {
    curriculumId: 2,
    essayAnswers: [
      {
        id: 71,
        keywordId: 1,
        quiz: {
          quizId: 29,
          isLearning: true,
          question: 'Varargs는 어떻게 사용하고, 사용했을 때 장점은 무엇인가요?',
        },
        answer:
          '수를 가변적으로 조절할 수 있게 해주는 기능이다.\n<br>\n\n## 사용법\n***\nparameter를 여러개 정의하고 싶은 method를 선언할 때, parameter 부분에 ...을 붙이면 된다.\n\n```java\npublic int calculateSum(int... numbers) {\n    return Arrays.stream(numbers).sum();\n}\n```\n\n위 코드에서 유추할 수 있듯이, varargs는 내부적으로 array를 반환한다.\ncompile 시에 Object array가 만들어지고, varargs에 할당된 인자들이 array의 원소로 들어간다.\n<br>\n\n## 장점\n***\n만약 위의 method에서, varargs를 사용하지 않는다고 가정해보자.\n그렇다면, parameter가 2개, 3개, 4개, ..., 인 calculateSum() method를 전부 overloading해야 할 것이다.\n\n```java\npublic int getSum(int number1, int number2) {\n\treturn number1 + number2;\n}\npublic int getSum(int number1, int number2, int number3) {\n\treturn number1 + number2 + number3;\n}\n\n...\n\n\n```\n\nvarargs를 사용하면 이러한 overloading을 하지 않도록 할 수 있다.',
        author: {
          id: 304,
          username: 'Jaeyoung22',
          nickname: 'ReO',
          role: 'CREW',
          imageUrl: 'https://avatars.githubusercontent.com/u/89302528?v=4',
        },
        createdAt: '2023-04-09T17:56:25.642891',
        updatedAt: '2023-04-09T17:56:25.642891',
      },
      {
        id: 69,
        keywordId: 1,
        quiz: {
          quizId: 29,
          isLearning: false,
          question: 'Vara하고, 사용했을 때 장점은 무엇인가요?',
        },
        answer:
          '# Va습니다.\n이를 해결하기 위해선 두 가지 방식을 사용했습니다.\n1. 메서드 오버로딩\n이 방법은 코드의 길이를 무한대로 증가시킵니다.\n2. 전달할 값들을 배열로 받기\n\n결국, Varargs는 **다양한 범위의 인자를 받기 위한 해결책**으로 탄생하게 되었습니다.\n\n# Varargs 사용법 및 특징\n\n```java\npublic class VarargsPractice {\n    public static void main(String[] args) {\n        printValues(); // 특징 1. 0개 이상의 인자를 전달할 수 있습니다.\n        printValues("1");\n        printValues("1", "2");\n        printValues("1", "2", "3");\n        printValues(new String[]{"1", "2", "3"}); // 특징 2. 실제로는 배열로 동작합니다.\n    }\n\n    // 사용법: 파라미터에 타입과 ... 을 함께 작성합니다. (String...)\n    public static void printValues(final String... values) {\n        for (int i = 0; i < values.length; i++) {\n            System.out.println(values[i]); // 배열로 동작하기에 인덱스로 접근 가능합니다.\n        }\n    }\n}\n```\n\n# 잘못된 Varargs 사용법\n\n1. 한 메서드에 2가지 타입의 가변인자(varargs)는 사용하지 못합니다.\n```java\nvoid method(String... inputs, int... numbers);\n```\n\n2. 가변인자를 첫 번째 파라미터로 선언하지 못합니다.\n```java\nvoid method(int... numbers, String input);\n```\n\n# Varargs의 장점\n\n1. 메서드 인자의 수를 동적으로 결정 및 처리할 수 있습니다.\n2. 코드를 간결하게 유지할 수 있습니다.\n\n# Varargs의 단점\n\n1. 공간 복잡도가 증가합니다.\n가변 인자는 곧, 배열입니다.\n가변 인자를 전달받은 메서드는 매번 새로운 배열을 생성해야 합니다.\n\n# Varargs를 사용하는 기준\n(개인적인 기준입니다.)\n\n1. 인자의 개수를 다양하게 받아야 하는 상황이다.\n2. 성능상 그다지 중요하지 않은 상황이다.',
        author: {
          id: 287,
          username: 'kdkdhoho',
          nickname: '도기\uD83D\uDC36',
          role: 'CREW',
          imageUrl: 'https://avatars.githubusercontent.com/u/66300965?v=4',
        },
        createdAt: '2023-04-08T23:54:48.667266',
        updatedAt: '2023-04-08T23:54:48.667266',
      },
    ],
  },
];

const essayAnswersMock = {
  data,
  filter(params: EssayAnswersFilterParams) {
    const { curriculumId, keywordId, memberIds, page = 1, quizIds, size = 10 } = params;

    const essayAnswers =
      this.data.find(
        (essayAnswersByCurriculum) => essayAnswersByCurriculum.curriculumId === curriculumId
      )?.essayAnswers ?? [];

    return essayAnswers
      .filter((essayAnswer) => {
        if (keywordId && essayAnswer.keywordId !== keywordId) return false;

        if (memberIds && !memberIds.includes(essayAnswer.author.id)) return false;

        if (quizIds && quizIds.includes(essayAnswer.quiz.quizId)) return false;

        return true;
      })
      .slice(0, size);
  },
};

export default essayAnswersMock;
