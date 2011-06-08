window.fireEvent(element,event){
if (document.createEventObject){
// dispatch for IE
var evt = document.createEventObject();
return element.fireEvent('on'+event,evt)
}
else{
// dispatch for firefox + others
var evt = document.createEvent("HTMLEvents");
evt.initEvent(event, true, true ); // event type,bubbling,cancelable
return !element.dispatchEvent(evt);
}