/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { COLOR } from '../../enumerations/color';
import ResponsiveButton from './ResponsiveButton';

export default {
  title: 'Component/ResponsiveButton',
  component: ResponsiveButton,
  argTypes: {},
};

const Template = (size) => (args) => (
  <div
    css={css`
      width: ${size}px;
    `}
  >
    <ResponsiveButton {...args} />
  </div>
);

const SessionTemplate = Template(135);

export const Session = SessionTemplate.bind({});

Session.args = {
  text: '프론트엔드 세션1',
  color: 'white',
  backgroundColor: COLOR.LIGHT_BLUE_900,
  height: '36px',
};

const Depth1Template = Template(820);

export const Depth1 = Depth1Template.bind({});

Depth1.args = {
  text: 'JavaScript',
  color: 'white',
  backgroundColor: COLOR.LIGHT_BLUE_800,
  height: '50px',
};

const Depth2Template = Template(386);

export const Depth2 = Depth2Template.bind({});

Depth2.args = {
  text: 'JavaScript',
  color: 'white',
  backgroundColor: COLOR.LIGHT_BLUE_500,
  height: '50px',
};
