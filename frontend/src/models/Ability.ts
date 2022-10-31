export interface Ability {
  id: number;
  name: string;
  description: string;
  color: string;
  isParent: boolean;
}

export interface ParentAbility extends Ability {
  children: Ability[];
}

export interface StudyLogWithAbilityRequest {
  id: number;
  abilities: string[];
}

export interface StudyLogWithAbilityResponse {
  id: number;
  abilities: Ability[];
}

//TODO : 역량이력에 대한 interface를 작성한다.
// id, 날짜, abilities: ParentAbility[], StudyLogs: StudyLogWithAbilityResponse[] 이렇게 되지 않을까 생각..
