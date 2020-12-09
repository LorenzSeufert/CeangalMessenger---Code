const {app, BrowserWindow, Menu, ipcMain} = require('electron')
const shell = require('electron').shell

let mainWindow;
let loginWindow;

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

    loginWindow.webContents.openDevTools()
    mainWindow.webContents.openDevTools()
}

ipcMain.on('entry-accepted', (event, arg) => {
    if(arg==='true'){
        mainWindow.show()
        loginWindow.hide()
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