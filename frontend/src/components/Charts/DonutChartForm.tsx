import React, { useEffect, useRef, useState } from 'react';

import { COLOR } from '../../constants';
import { CHART } from '../../constants/input';
import { canvasInit } from '../../utils/canvas';
import { getGrayscaleColor } from '../../utils/colors';
import { CategoryList, CategoryItem, Category, CategoryListTitle } from './DonutChartForm.styles';

interface ChartData {
  title: string;
  categoryTitle?: string;
  data: {
    id: number;
    name: string;
    weight: number;
    percentage: number;
    color: string;
    present: boolean;
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
  onChangeData: (
    data: {
      id: number;
      name: string;
      weight: number;
      percentage: number;
      color: string;
      present: boolean;
    }[]
  ) => void;
}

const DEFAULT_CATEGORY_VALUE = -1;
const DEFAULT_CANVAS_WIDTH = 300;
const DEFAULT_CANVAS_HEIGHT = 300;

const DonutChartForm = ({ chartData, config, onChangeData }: Props) => {
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
      present: boolean;
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

  const toggleIsPresent = (id: number) => {
    const toggledData = graphData.map((item) => {
      if (item.id === id) {
        return { ...item, present: !item.present };
      }
      return item;
    });

    const sumOfWeights = toggledData.reduce(
      (sum, { weight, present }) => sum + (present ? weight : 0),
      0
    );
    const resultData = toggledData.map((item) => {
      if (item.present) {
        return { ...item, percentage: Number((item.weight / sumOfWeights).toFixed(2)) };
      }
      return { ...item, percentage: 0 };
    });

    setGraphData(resultData);
  };

  const changeWeight = (id: number, value: number) => {
    const data = graphData.map((item) => {
      if (item.id === id) {
        return { ...item, weight: Number(value) };
      }
      return item;
    });
    const sumOfWeights = data.reduce((sum, { weight, present }) => sum + (present ? weight : 0), 0);
    const resultData = data.map((item) => {
      if (item.present) {
        return { ...item, percentage: Number((item.weight / sumOfWeights).toFixed(2)) };
      }
      return { ...item, percentage: 0 };
    });

    setGraphData(resultData);
  };

  // 정확한 계산상의 어려움으로 사용 보류
  // const changePercentage = (id: number, value: number) => {
  //   if (value === 1) {
  //     setGraphData(
  //       graphData.map((item) => {
  //         if (item.id === id) {
  //           return { ...item, weight: 100, percentage: Number(value) };
  //         }

  //         return { ...item, weight: 0, percentage: 0 };
  //       })
  //     );

  //     return;
  //   }

  //   const prevPercentage = graphData.find((item) => item.id === id)?.percentage || 0;
  //   const gap = value - prevPercentage;
  //   const presentData = graphData.filter((item) => item.present);

  //   const data = graphData.map((item) => {
  //     if (item.id === id) {
  //       return { ...item, percentage: Number(value) };
  //     }

  //     if (item.present) {
  //       const calculatedPercentage = Number(
  //         (item.percentage - gap / (presentData.length > 1 ? presentData.length - 1 : 1)).toFixed(2)
  //       );

  //       return {
  //         ...item,
  //         percentage: calculatedPercentage > 0 ? calculatedPercentage : 0,
  //       };
  //     }

  //     return { ...item, percentage: 0 };
  //   });
  //   const resultData = data.map((item) => {
  //     if (item.present) {
  //       return { ...item, weight: 100 * item.percentage };
  //     }
  //     return { ...item, weight: 0 };
  //   });

  //   setGraphData(resultData);
  // };

  useEffect(() => {
    if (!canvasRef.current) {
      return;
    }

    draw(graphData.filter((item) => item.present));
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
        <form>
          {!!categoryTitle && <h4>{categoryTitle}</h4>}
          <CategoryList>
            <CategoryListTitle>
              <div>표시</div>
              <div>역량</div>
              <div>가중치</div>
              <div>비율(%)</div>
            </CategoryListTitle>
            {graphData.map(({ id, name, color, weight, percentage, present }) => (
              <CategoryItem
                key={id}
                chipColor={color}
                checked={id === currentCategory}
                currentCategory={currentCategory}
              >
                <div>
                  <input
                    type="checkbox"
                    checked={present}
                    onChange={() => toggleIsPresent(id)}
                    onBlur={() => onChangeData(graphData)}
                  />
                </div>
                <label>
                  <span />
                  {name}
                </label>
                <div>
                  <input
                    type="number"
                    min={CHART.ABILITY_WEIGHT.MIN}
                    max={CHART.ABILITY_WEIGHT.MAX}
                    value={weight}
                    onChange={(event) => changeWeight(id, Number(event.target.value))}
                    onBlur={() => onChangeData(graphData)}
                  />
                </div>

                <div>
                  {/* 
                    * 정확한 계산상의 어려움으로 사용 보류
                  <input
                    type="number"
                    min={0}
                    max={1}
                    step={0.1}
                    value={percentage}
                    onChange={(event) => changePercentage(id, Number(event.target.value))}
                  /> */}

                  <p>{percentage}</p>
                </div>
              </CategoryItem>
            ))}
          </CategoryList>
        </form>
      </Category>
    </div>
  );
};

export default React.memo(DonutChartForm);
