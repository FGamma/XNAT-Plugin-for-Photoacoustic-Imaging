# XNAT Photoacoustic Imaging Plugin

XNAT plugin that adds support for DICOM Photoacoustic Imaging sessions in XNAT.

This project is based on the `xnat-template-plugin` structure.

## Features

- Defines the new `xnat:photoacousticimagingSessionData` data type.
- Defines the related `xnat:photoacousticimagingScanData` scan type.
- Includes minimal Velocity templates for the XNAT report and edit screens for the new data type.

## Build

On Linux/macOS:

```bash
./gradlew xnatDataBuilder xnatPluginJar
```

On Windows PowerShell:

```powershell
.\gradlew.bat xnatDataBuilder xnatPluginJar
```

The installable plugin jar is generated in:

```text
build/libs/
```

With the current configuration, the expected filename is similar to:

```text
photoacoustic-imaging-plugin-1.0.0-SNAPSHOT-xpl.jar
```

## Installation in XNAT

1. Build the plugin.
2. Stop Tomcat/XNAT.
3. Copy the generated `*-xpl.jar` file into:

   ```text
   ${xnat.home}/plugins
   ```

4. Restart Tomcat/XNAT.
5. Log in as an administrator.
6. Go to `Administer > Data Types`.
7. Use `Set up additional data type` to enable the new data type if XNAT requires it.

```

## DICOM Behavior

The `XnatPhotoacousticimagingsessiondataBeanFactory` and `XnatPhotoacousticimagingscandataBeanFactory` classes read the DICOM `Modality` tag.

When a session or series contains a single modality and the value is:

```text
PAI
```

the plugin creates:

```text
XnatPhotoacousticimagingsessiondataBean
XnatPhotoacousticimagingscandataBean
```

## XNAT Schema

The main schema is:

```text
src/main/resources/schemas/photoacousticimaging/photoacousticimaging.xsd
```

It defines:

- `xnat:PHOTOACOUSTICIMAGINGSession`
- `xnat:photoacousticimagingSessionData`, extending `xnat:imageSessionData`
- `xnat:photoacousticimagingScanData`, extending `xnat:imageScanData`

## Troubleshooting

### The plugin does not load

- Make sure the jar copied into `${xnat.home}/plugins` ends with `-xpl.jar`.
- Check the Tomcat/XNAT startup logs.
- Confirm that the target XNAT version is compatible with the versions configured in `build.gradle`.

### The data type does not appear in XNAT

- Make sure `xnatDataBuilder` ran before creating the plugin jar.
- Confirm that `photoacousticimaging.xsd` is included in the jar.
- After restarting XNAT, log in as an administrator and check `Administer > Data Types`.

### DICOM files are not classified as Photoacoustic Imaging

- Make sure the DICOM `Modality` tag is set to `PAI`.
- Confirm that all instances in the same session or series have a consistent modality.
- Check the XNAT prearchive/import logs.

## References

- XNAT discussion about Modality/Ophthalmic Photography and plugin build notes: https://groups.google.com/g/xnat_discussion/c/fjHOmjwCIC4/m/cwo18FDsAQAJ
- XNAT Template Plugin: https://bitbucket.org/xnatx/xnat-template-plugin/src/master/
- XNAT documentation for creating plugins: https://wiki.xnat.org/documentation/creating-an-xnat-plugin-project
- XNAT documentation for deploying plugins: https://wiki.xnat.org/documentation/deploying-plugins-in-xnat
