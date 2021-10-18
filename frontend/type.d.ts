declare namespace Report {
  type Ability = {
    id: number;
    name: string;
    weight: number;
    percentage: number;
    color: string;
    isPresent: boolean;
  };

  interface AbilityGraph {
    abilities: Ability[];
  }
}
