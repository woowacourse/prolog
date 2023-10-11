interface RoadmapSelectedFilterProps {
  itemName: string;
  data: string[];
  handleFilter: (filterName, filterItem) => void;
}

const RoadmapSelectedFilter = ({ itemName, data, handleFilter }: RoadmapSelectedFilterProps) => {
  return (
    <ul>
      {data &&
        data.map((item) => {
          return <li onClick={() => handleFilter(itemName, item)}>{item}</li>;
        })}
    </ul>
  );
};

export default RoadmapSelectedFilter;
