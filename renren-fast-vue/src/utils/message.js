import { MessageBox, Message, Notification } from "element-ui"

// 普通消息
export function ErrorMessage(msg) {
  Message({
    type: "error",
    message: msg
  })
}
export function SuccessMessage(msg, callback) {
  Message({
    type: "success",
    message: msg,
    onClose: () => {
      if (callback && typeof callback === 'function') {
        callback();
      }
    }
  })
}

export function InfoMessage(msg) {
  Message({
    type: "info",
    message: msg
  })
}

// end 普通消息
// 弹窗回调消息
function ConfirmMessageBox(type, callback, msg, title = '提示', confirmButtonText = '确认', cancelButtonText = '取消') {
  MessageBox.confirm(msg, title, {
    confirmButtonText,
    cancelButtonText,
    type: type ,
    center: true
  }).then(() => {
    console.log(callback , "callback")
    if (callback && typeof callback === 'function') {
      console.log('回调执行 :>> ');
      callback();
    }
  }).catch((e) => {
    console.log('e :>> ', e);
    InfoMessage("已取消")
  })

}
/**
 * 
 * @param {Function} callback  确认回调函数 
 * @param {String} msg 消息内容
 * @param {String} title 标题
 * @param {String} confirmButton Text
 * @param {String} canclButton Text
 */
export function WarningConfirm(callback, msg = '警告', title = '提示', confirmButtonText = '确认', canclButtonText = '取消') {
  ConfirmMessageBox('waring', callback, msg);
}


// end 弹窗回调消息
// 多个消息Notification
export function ErrorNotification(msg, title = '错误', duration = 4500) {
  Notification({
    title,
    message: msg,
    type: 'error',
    position: 'top-right',
    duration
  })
}





