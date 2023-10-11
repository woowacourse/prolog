import { ImportanceColors } from '../../colors';
import { Container, LegendItem, LegendItemList } from './ImportanceLegend.styles';

const ImportanceLegend = () => {
  return (
    <Container>
      <p>중요도 범례</p>
      <p>낮음</p>
      <LegendItemList>
        {Object.entries(ImportanceColors).map(([importance, color]) => (
          <LegendItem key={importance} $color={color} aria-label={`중요도 ${importance}`} />
        ))}
      </LegendItemList>
      <p>높음</p>
    </Container>
  );
};

export default ImportanceLegend;
