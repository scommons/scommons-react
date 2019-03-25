const React = require('react');

module.exports = {
  
  create: function (
    displayName,
    renderDef,
    getInitialState,
    componentDidMountDef,
    shouldComponentUpdateDef,
    componentDidUpdateDef,
    componentWillUnmountDef) {
    
    class ReactComponentImpl extends React.Component {
      constructor(props) {
        super(props);
        this.state = getInitialState.call(this);
      }
    
      componentDidMount() {
        componentDidMountDef.call(this);
      }
      
      shouldComponentUpdate(nextProps, nextState) {
        return shouldComponentUpdateDef.call(this, nextProps, nextState);
      }
      
      componentDidUpdate(prevProps, prevState) {
        componentDidUpdateDef.call(this, prevProps, prevState);
      }
      
      componentWillUnmount() {
        componentWillUnmountDef.call(this);
      }
      
      render() {
        return renderDef.call(this);
      }
    };
    
    ReactComponentImpl.displayName = displayName;
    ReactComponentImpl.toString = function () {
      return displayName;
    };
    
    return ReactComponentImpl;
  }
};
