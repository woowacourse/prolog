import { COLOR } from '../constants';

const canvasInit = (canvasElement: HTMLCanvasElement, backgroundColor = COLOR.WHITE) => {
  const context = canvasElement.getContext('2d');

  if (!context) {
    return;
  }

  const width = canvasElement.width;
  const height = canvasElement.height;

  context.fillStyle = backgroundColor;
  context.fillRect(0, 0, width, height);
};

export { canvasInit };
