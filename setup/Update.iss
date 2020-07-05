#include "define.h"

[Setup]
AppName={#APPLICATION_NAME}
AppVersion={#APPLICATION_VERSION}
AppCopyright=© Evalutel. All rights reserved
VersionInfoVersion={#FILE_VERSION}
DefaultDirName={#INSTALL_DIR}
AppendDefaultDirName=no
AllowRootDirectory=yes
DirExistsWarning=no
OutputDir=Release\{#APPLICATION_VERSION}
OutputBaseFilename={#UPDATE_NAME}
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
Name: "fr"; MessagesFile: "compiler:Languages\French.isl,Languages\FrenchUpdate.isl"

[Files]
Source: "Launcher\{#EXECUTABLE_NAME}"; DestDir: "{app}"; Flags: ignoreversion overwritereadonly
Source: "Prog\*"; DestDir: "{app}\{#APPLICATION_DIR}"; Flags: ignoreversion overwritereadonly

[Run]
Filename: "{app}\{#EXECUTABLE_NAME}"; Description: "{cm:LaunchProgram,{#StringChange(APPLICATION_NAME, '&', '&&')}}"; Flags: nowait postinstall skipifsilent

[Code]
function IsAppInstalledDir(Dir: String): Boolean;
begin
  Result := FileExists(Dir + '\{#EXECUTABLE_NAME}');
end;

procedure InitializeWizard();
begin
  if IsAppInstalledDir(ExpandConstant('{src}')) then begin
    WizardForm.DirEdit.Text := ExpandConstant('{src}');
  end else if IsAppInstalledDir(ExtractFileDir(ExpandConstant('{src}'))) then begin
    WizardForm.DirEdit.Text := ExtractFileDir(ExpandConstant('{src}'));
  end else begin
    WizardForm.DirEdit.Text := '{#INSTALL_DIR}';
  end;
end;

function NextButtonClick(CurPageID: Integer): Boolean;
begin
  Result := True;
  if (CurPageID = wpSelectDir) then begin
    if Not IsAppInstalledDir(ExpandConstant('{app}')) then begin
      MsgBox('Impossible de continuer la mise à jour car {#APPLICATION_NAME} n''est pas installé dans le répertoire sélectionné.', mbError, MB_OK);
      Result := False;
    end;
  end;
end;

