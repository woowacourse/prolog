/** @jsxImportSource @emotion/react */

import * as d3 from 'd3';
import { KeywordResponse } from '../../../../models/Keywords';
import SubKeyword from './SubKeyword';
import MainKeyword from './MainKeyword';
import { hsl, KeywordColors } from '../../colors';

type KeywordPosition = 'left' | 'right';

type Keyword = KeywordResponse;

type RoadmapProps = {
  hue: number;
  keywords: KeywordResponse[];
  width: number;
  onClick: (keyword: KeywordResponse) => void;
};

type SubKeywordsProps = {
  hue: number;
  subKeywordWidth: number;
  subKeywordHeight: number;
  subKeywordsGap: number;
  childrenInterval: number;
  keywords: Keyword[];
  position: KeywordPosition;

  onClick: (keyword: KeywordResponse) => void;
};

const createSubKeywords = (props: SubKeywordsProps): { height: number; child: React.ReactNode } => {
  const {
    hue,
    subKeywordWidth,
    subKeywordHeight,
    subKeywordsGap,
    childrenInterval,
    keywords,
    position,
    onClick,
  } = props;

  const subKeywordOffset =
    position === 'left' ? -childrenInterval + -subKeywordWidth : childrenInterval;

  const { childrenHeight, children } = keywords.reduce(
    ({ childrenHeight, children }, keyword, index) => {
      const { height: childHeight, child } = createSubKeywords({
        hue,
        subKeywordWidth,
        subKeywordHeight,
        subKeywordsGap,
        childrenInterval,
        keywords: keyword.childrenKeywords,
        position,
        onClick,
      });

      const pathStartY = d3
        .scaleLinear()
        .domain([0, keywords.length])
        .range([subKeywordHeight * -0.4, subKeywordHeight * 0.4]);

      const pathFromMainToSubKeyword = (index: number, childrenHeight: number) => {
        const [x1, y1] = [0, pathStartY(index)];
        const [x2, y2] = [childrenInterval * (position === 'left' ? -1 : 1), childrenHeight];

        const curve = d3.line().curve(d3.curveNatural);
        return (
          curve([
            [x1, y1],
            [(x1 + x2) / 2, y2 * 0.8],
            [x2, y2],
          ])?.toString() ?? ''
        );
      };

      return {
        childrenHeight:
          // 기존 누산
          childrenHeight +
          // 자식의 높이를 합산한 높이
          childHeight +
          // 자식이 있으면 자식 높이, 없으면 본인 높이가 곧 본인과 하위 트리의 높이임
          (childHeight === 0 ? subKeywordHeight : 0) +
          // 본인과 형제자매 사이의 간격
          subKeywordsGap,
        children: [
          ...children,
          <>
            <path
              d={pathFromMainToSubKeyword(index, childrenHeight)}
              stroke={hsl(KeywordColors.LINE)}
              strokeDasharray="2 4"
              strokeWidth={2}
              fill="none"
            />
            <g
              transform={`translate(${subKeywordOffset}, ${childrenHeight})`}
              onClick={() => onClick(keyword)}
            >
              <foreignObject
                x={0}
                y={-(subKeywordHeight / 2)}
                width={subKeywordWidth}
                height={subKeywordHeight}
                overflow="visible"
              >
                <SubKeyword keyword={keyword} hue={hue} onClick={onClick} />
              </foreignObject>
            </g>
            <g
              transform={`translate(${
                subKeywordOffset + (position === 'left' ? 0 : subKeywordWidth)
              }, ${childrenHeight})`}
            >
              {child}
            </g>
          </>,
        ],
      };
    },
    { childrenHeight: 0, children: [] } as {
      childrenHeight: number;
      children: React.ReactNode[];
    }
  );

  const height = childrenHeight;

  return {
    height: height,
    child: children,
  };
};

const Roadmap = (props: RoadmapProps) => {
  const { keywords: data, width, hue, onClick } = props;

  const mainKeywordWidth = 200;
  const mainKeywordHeight = 50;

  const subKeywordWidth = 120;
  const subKeywordHeight = 30;
  const subKeywordsGap = 10;

  const childrenInterval = 40;

  const miniumGapBetweenMainKeywords = 200;

  const fromPreviousMainKeywordArcArrow = (start: [number, number], end: [number, number]) => {
    const [x1, y1] = start;
    const [x2, y2] = end;
    const [xDiff, yDiff] = [x2 - x1, y2 - y1];

    const curve = d3.line().curve(d3.curveNatural);
    return (
      curve([
        [x1, y1],
        [x1 + xDiff * (1 / 5), y1 + yDiff * (1 / 3)],
        [x1 + xDiff * (4 / 5), y1 + yDiff * (2 / 3)],
        [x2, y2],
      ])?.toString() ?? ''
    );
  };

  const treeHeights = {
    left: 0,
    right: 0,
    lastCoordinate: [0, 0] as [number, number],
  };

  const roadmapTree = (
    <g transform={`translate(${width / 2}, 0)`}>
      {data.map((keyword) => {
        const position = treeHeights.left > treeHeights.right ? 'right' : 'left';
        const oppositePosition = position === 'left' ? 'right' : 'left';
        const horizontalOffset = position === 'left' ? -80 : 80;

        const { height, child } = createSubKeywords({
          childrenInterval,
          subKeywordHeight,
          subKeywordWidth,
          subKeywordsGap,
          keywords: keyword.childrenKeywords,
          position,
          onClick,
          hue,
        });

        const oppositeTreeHeightDiff = Math.abs(
          treeHeights[position] - treeHeights[oppositePosition]
        );
        if (oppositeTreeHeightDiff < miniumGapBetweenMainKeywords) {
          treeHeights[oppositePosition] = treeHeights[position] + miniumGapBetweenMainKeywords;
        }

        const subKeywordsTotalHeight = treeHeights[position];

        // 다음 메인 키워드의 위치를 정하기 위해 누산
        treeHeights[position] += Math.max(height, miniumGapBetweenMainKeywords);

        // 이전 메인 키워드에서 자기한테 오는 수직 뱡향 곡선 화살표의 시작점과 끝점
        const start: [number, number] = [
          treeHeights.lastCoordinate[0],
          -(subKeywordsTotalHeight - treeHeights.lastCoordinate[1]) + mainKeywordHeight,
        ];
        const end: [number, number] = [horizontalOffset, 0];

        // 다음 메인 키워드를 위한 시작점을 저장
        treeHeights.lastCoordinate = [horizontalOffset, subKeywordsTotalHeight];

        return (
          <>
            <g transform={`translate(0, ${subKeywordsTotalHeight})`}>
              <path
                d={fromPreviousMainKeywordArcArrow(start, end)}
                stroke={hsl(KeywordColors.LINE)}
                strokeWidth={4}
                fill="transparent"
                markerEnd="url(#arrow-head)"
              />
            </g>
            <g transform={`translate(${horizontalOffset}, ${subKeywordsTotalHeight})`}>
              <foreignObject
                x={-mainKeywordWidth / 2}
                y={0}
                width={mainKeywordWidth}
                height={mainKeywordHeight}
                onClick={() => onClick(keyword)}
              >
                <MainKeyword keyword={keyword} hue={hue} onClick={onClick} />
              </foreignObject>

              <g
                transform={`translate(${(mainKeywordWidth / 2) * (position === 'left' ? -1 : 1)}, ${
                  mainKeywordHeight / 2
                })`}
              >
                {child}
              </g>
            </g>
          </>
        );
      })}
    </g>
  );

  return (
    <svg width={width} height={Math.max(treeHeights.left, treeHeights.right)}>
      <defs>
        <marker
          id="arrow-head"
          markerWidth="4"
          markerHeight="4"
          viewBox="0 0 4 4"
          refX="2"
          refY="2"
          orient="auto"
          opacity={0}
        >
          <path
            d={d3
              .line()([
                [0, 0],
                [0, 4],
                [4, 2],
              ])
              ?.toString()}
            fill="black"
          />
        </marker>
      </defs>

      {roadmapTree}
    </svg>
  );
};

export default Roadmap;
