const {app, BrowserWindow, protocol} = require('electron')
require('electron-reload')(__dirname);
const express = require('./src/app')
const path = require('path')

function createWindow() {
  const win = new BrowserWindow({
    width: 900,
    height: 700,
    resizable: true,
    icon: __dirname + '/src/img/CeangalLogoPNG.png',
    webPreferences: {
      nodeIntegration: true,
      nodeIntegrationInWorker: true,
      webSecurity: false,
    }
  })
  win.loadURL("http://localhost:3841/")
  win.removeMenu();

  // win.webContents.openDevTools()
}

app.whenReady().then(() => {
  createWindow()

  app.on('activate', () => {
    if (BrowserWindow.getAllWindows().length === 0) {
      createWindow()
    }
  })
})

app.whenReady().then(() => {
  protocol.registerFileProtocol('file', (request, callback) => {
    const pathname = request.url.replace('file:///', '');
    callback(pathname);
  });
});

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit()
  }
})
