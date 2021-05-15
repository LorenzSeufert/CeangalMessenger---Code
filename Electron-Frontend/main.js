const {app, BrowserWindow} = require('electron')
require('electron-reload')(__dirname);
const express = require('./src/app')
const path = require('path')

function createWindow() {
  const win = new BrowserWindow({
    width: 1100,
    height: 800,
    resizable: true,
    icon: __dirname + '/src/pictures/CeangalLogoPNG.png',
    webPreferences: {
    }
  })
  win.loadURL("http://localhost:3841/")
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
