import { useEffect, useRef, useState } from 'react';

import { COLOR } from '../../constants';
import { canvasInit } from '../../utils/canvas';
import { getGrayscaleColor } from '../../utils/colors';
import { CategoryList, CategoryItem, Category } from './Chart.styles';

interface ChartData {
  title: string;
  categoryTitle?: string;
  data: {
    id: number;
    name: string;
    weight: number;
    percentage: number;
    color: string;
    isPresent: boolean;
  }[];
}

interface CanvasConfig {
  width?: number;
  height?: number;
  backgroundColor?: string;
}

interface Props {
  chartData: ChartData;
  config: CanvasConfig;
}

const DEFAULT_CATEGORY_VALUE = -1;
const DEFAULT_CANVAS_WIDTH = 300;
const DEFAULT_CANVAS_HEIGHT = 300;

const DonutChart = ({ chartData, config }: Props) => {
  const { title, categoryTitle, data } = chartData;
  const {
    width = DEFAULT_CANVAS_WIDTH,
    height = DEFAULT_CANVAS_HEIGHT,
    backgroundColor = COLOR.WHITE,
  } = config;

  const canvasRef = useRef<HTMLCanvasElement>(null);

  const [currentCategory, setCurrentCategory] = useState(DEFAULT_CATEGORY_VALUE);
  const [graphData, setGraphData] = useState(chartData.data || []);

  const draw = (
    data: {
      id: number;
      name: string;
      weight: number;
      percentage: number;
      color: string;
      isPresent: boolean;
    }[]
  ) => {
    if (!canvasRef.current) {
      return;
    }

    canvasInit(canvasRef.current, backgroundColor);

    const context = canvasRef.current.getContext('2d') as CanvasRenderingContext2D;

    const midX = width / 2;
    const midY = height / 2;
    const radius = midX > midY ? midY * 0.8 : midX * 0.8;

    if (!data.length) {
      context.beginPath();
      context.arc(midX, midY, radius, 0, 2 * Math.PI);
      context.fillStyle = COLOR.LIGHT_GRAY_300;
      context.fill();

      context.beginPath();
      context.arc(midX, midY, radius / 2, 0, 2 * Math.PI);
      context.fillStyle = backgroundColor;
      context.fill();

      return;
    }

    let sumOfPercentage = 0;

    data.forEach(({ percentage, color }) => {
      context.beginPath();
      context.arc(midX, midY, radius, 0, (2 - sumOfPercentage * 2) * Math.PI);
      context.lineTo(midX, midY);
      context.fillStyle = color;
      context.fill();

      sumOfPercentage = sumOfPercentage + percentage;
    });

    context.beginPath();
    context.arc(midX, midY, radius / 2, 0, 2 * Math.PI);
    context.fillStyle = backgroundColor;
    context.fill();
  };

  useEffect(() => {
    if (!canvasRef.current) {
      return;
    }

    const filteredData =
      currentCategory === DEFAULT_CATEGORY_VALUE
        ? graphData
        : graphData.map((item, index) => {
            if (currentCategory !== item.id) {
              return { ...item, color: getGrayscaleColor(item.color) };
            }

            return item;
          });

    draw(filteredData);
  }, [currentCategory, graphData]);

  useEffect(() => {
    if (data) {
      setGraphData(data);
    }
  }, [data]);

  return (
    <div
      style={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        width: '100%',
        height: '100%',
      }}
      title={title}
    >
      <div>
        <canvas
          width={width}
          height={height}
          ref={canvasRef}
          onClick={(event) => {
            console.dir(event);
          }}
        >
          캔버스를 제공하지 않는 브라우저입니다.
        </canvas>
      </div>
      <Category>
        {!!categoryTitle && <h4>{categoryTitle}</h4>}
        <CategoryList>
          <li hidden>
            <input
              type="radio"
              name="category"
              value={DEFAULT_CATEGORY_VALUE}
              checked={currentCategory === DEFAULT_CATEGORY_VALUE}
              readOnly
            />
          </li>
          {graphData.map(({ id, name, color }) => (
            <CategoryItem
              key={id}
              chipColor={color}
              checked={id === currentCategory}
              currentCategory={currentCategory}
            >
              <label>
                <input
                  name="category"
                  type="radio"
                  value={id}
                  onChange={() => setCurrentCategory(id)}
                />
                <span />
                {name}
              </label>
            </CategoryItem>
          ))}
        </CategoryList>
        <div>
          <button type="button" onClick={() => setCurrentCategory(DEFAULT_CATEGORY_VALUE)}>
            reset
          </button>
        </div>
      </Category>
    </div>
  );
};

export default DonutChart;
