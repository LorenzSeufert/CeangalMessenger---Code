const {app, BrowserWindow, Menu} = require('electron')
const shell = require('electron').shell

function createWindow() {
    const mainWindow = new BrowserWindow({
        width: 800,
        height: 600,
        show: true,
        webPreferences: {
            nodeIntegration: true
        }
    })
    mainWindow.loadFile('src/index.html')

    /*const loginWindow = new BrowserWindow({
        parent: mainWindow,
        width: 400,
        height: 300,
        frame: false
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
    Menu.setApplicationMenu(menu);*/

}

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