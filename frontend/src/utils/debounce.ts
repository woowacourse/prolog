const debounce = (() => {
  let id = 0;

  return (callback: (...args: unknown[]) => void, ms?: number) => {
    if (id) {
      window.clearTimeout(id);
    }

    id = window.setTimeout(callback, ms);
  };
})();

export default debounce;
