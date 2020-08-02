import {MessageBox , Message } from "element-ui"

export function ErrorMessage (msg) {
  Message({
    type: "error",
    message: msg
  })
}
export function SuccessMessage(msg) {
  Message({
    type: "success",
    message: msg
  })
}

export function InfoMessage(msg) {
  Message({
    type: "info",
    message: msg
  })
}

