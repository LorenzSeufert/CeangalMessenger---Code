const {app, BrowserWindow, Menu, ipcMain} = require('electron')
const shell = require('electron').shell

let mainWindow;
let loginWindow;
let editPage;

function createWindow() {
  mainWindow = new BrowserWindow({
    width: 800,
    height: 600,
    show: false,
    webPreferences: {
      nodeIntegration: true
    }
  })
  mainWindow.loadFile('src/index.html')
  // mainWindow.webContents.openDevTools()

  loginWindow = new BrowserWindow({
    parent: mainWindow,
    width: 400,
    height: 300,
    frame: false,
    webPreferences: {
      nodeIntegration: true
    }
  })
  loginWindow.loadFile('src/login.html')
  // loginWindow.webContents.openDevTools()

  editPage = new BrowserWindow({
    parent: mainWindow,
    width: 600,
    height: 600,
    show: false,
    frame: false,
    webPreferences: {
      nodeIntegration: true
    }
  })
  editPage.loadFile('src/edit.html')
  // editPage.webContents.openDevTools()

  const menu = Menu.buildFromTemplate([
    {
      label: "Exit", click() {
        app.quit();
      }
    },
    {
      label: "Info",
      submenu: [
        {
          label: 'Documentation Repo', click() {
            shell.openExternal('https://github.com/LorenzSeufert/CeangalMessenger---Documentation')
          }
        },
        {
          label: 'Code Repo', click() {
            shell.openExternal('https://github.com/LorenzSeufert/CeangalMessenger---Code')
          }
        }
      ]
    }
  ]);
  Menu.setApplicationMenu(menu);
}

ipcMain.on('entry-accepted', (event, arg) => {
  if (arg === 'true') {
    mainWindow.show()
    loginWindow.hide()
  }
})

ipcMain.on('edit-profile', (event, arg) => {
  if (arg === 'true') {
    mainWindow.hide()
    editPage.show()
  }
})

ipcMain.on('save-edit', (event, arg) => {
  if (arg === 'true') {
    mainWindow.show()
    editPage.hide()
  }
})

app.whenReady().then(createWindow)

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) {
    createWindow()
  }
})