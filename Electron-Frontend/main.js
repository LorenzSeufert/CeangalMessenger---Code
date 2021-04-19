const {app, BrowserWindow} = require('electron')
const express = require('./src/app')
const path = require('path')

function createWindow() {
  const win = new BrowserWindow({
    width: 1000,
    height: 700,
    resizable: false,
    icon: __dirname + '/src/pictures/CeangalLogoPNG.png',
    webPreferences: {
    }
  })
  win.loadURL("http://localhost:3000/")
  win.removeMenu();
}

app.whenReady().then(() => {
  createWindow()

  app.on('activate', () => {
    if (BrowserWindow.getAllWindows().length === 0) {
      createWindow()
    }
  })
})

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit()
  }
})
