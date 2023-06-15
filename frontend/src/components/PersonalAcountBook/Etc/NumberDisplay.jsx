import React from 'react';
import numeral from 'numeral';

function NumberDisplay({ number }) {
  const formattedNumber = numeral(number).format('0.0a');

  return <div>{formattedNumber}</div>;
}

export default NumberDisplay;
