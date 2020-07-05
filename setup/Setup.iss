#include "define.h"

[Setup]
AppName={#APPLICATION_NAME}
AppVersion={#APPLICATION_VERSION}
AppCopyright=© Evalutel. All rights reserved
VersionInfoVersion={#FILE_VERSION}
DefaultGroupName=Evalutel
DisableProgramGroupPage=yes
DefaultDirName={#INSTALL_DIR}
AppendDefaultDirName=no
AllowRootDirectory=yes
DirExistsWarning=no
OutputDir=Release\{#APPLICATION_VERSION}
OutputBaseFilename={#SETUP_NAME}
Compression=lzma
SolidCompression=yes
PrivilegesRequired=lowest
CreateUninstallRegKey=no
UpdateUninstallLogAppName=no
Uninstallable=no
DisableWelcomePage=no
WizardImageFile=WizModernImage.bmp
WizardSmallImageFile=WizModernSmallImage.bmp

[Languages]
Name: "fr"; MessagesFile: "compiler:Languages\French.isl"

[Files]
Source: "Launcher\{#EXECUTABLE_NAME}"; DestDir: "{app}"; Flags: ignoreversion overwritereadonly
Source: "Prog\*"; DestDir: "{app}\{#APPLICATION_DIR}"; Flags: ignoreversion overwritereadonly

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Icons]
Name: "{userdesktop}\{#APPLICATION_NAME}"; Filename: "{app}\{#EXECUTABLE_NAME}"; Tasks: desktopicon;
;Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
;Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"

[Run]
Filename: "{app}\{#EXECUTABLE_NAME}"; Description: "{cm:LaunchProgram,{#StringChange(APPLICATION_NAME, '&', '&&')}}"; Flags: nowait postinstall skipifsilent
