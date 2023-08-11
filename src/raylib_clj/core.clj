(ns raylib-clojure.core
  (:require [coffi.ffi :as ffi]
            [coffi.mem :as mem :refer [defalias]])

(ffi/load-system-library "raylib")

(defalias ::vector2
  [::mem/struct
   [[:x ::mem/float]
    [:y ::mem/float]]])
  
(defalias ::image
  [::mem/struct
   [[:data ::mem/pointer]
    [:width ::mem/int]
    [:height ::mem/int]
    [:mipmaps ::mem/int]
    [:format ::mem/int]]])


; void InitWindow(int width, int height, const char *title);  
(ffi/defcfn init-window 
  "Initialize window and OpenGL context." 
  {:arglists '([width height title])}
  "InitWindow" [::mem/int ::mem/int ::mem/c-string] ::mem/void)

; bool WindowShouldClose(void);
(ffi/defcfn window-should-close? 
  "Check if KEY_ESCAPE pressed or Close icon pressed." 
  "WindowShouldClose" [] ::mem/int)

; void CloseWindow(void);
(ffi/defcfn close-window 
  "Close window and unload OpenGL context." 
  "CloseWindow" [] ::mem/void)

; bool IsWindowReady(void);
(ffi/defcfn window-ready?
  "Check if window has been initialized successfully."
  "IsWindowReady" [] ::mem/int)

; bool IsWindowFullscreen(void);
(ffi/defcfn window-fullscreen?
  "Check if window is currently fullscreen."
  "IsWindowFullscreen" [] ::mem/int)

; bool IsWindowHidden(void);
(ffi/defcfn window-hidden? 
  "Check if window is currently hidden (only PLATFORM_DESKTOP)."
  "IsWindowHidden" [] ::mem/int)

; bool IsWindowMinimized(void); 
(ffi/defcfn window-minimized? 
  "Check if window is currently minimized (only PLATFORM_DESKTOP)." 
  "IsWindowMinimized" [] ::mem/int)

; bool IsWindowMaximized(void);
(ffi/defcfn window-maximized? 
  "Check if window is currently maximized (only PLATFORM_DESKTOP)." 
  "IsWindowMaximized" [] ::mem/int)

; bool IsWindowFocused(void); 
(ffi/defcfn window-focused? 
  "Check if window is currently focused (only PLATFORM_DESKTOP)."
  "IsWindowFocused" [] ::mem/int)

; bool IsWindowResized(void);
(ffi/defcfn window-resized? 
  "Check if window has been resized last frame."
  "IsWindowResized" [] ::mem/int)

; bool IsWindowState(unsigned int flag);
(ffi/defcfn window-state?
  "Check if one specific window flag is enabled."
  {:arglists '([flag])}
  "IsWindowState" [] ::mem/int)

; void SetWindowState(unsigned int flags);
(ffi/defcfn set-window-state!
  "Set window configuration state using flags (only PLATFORM_DESKTOP)."
  {:arglists '([flags])}
  "SetWindowState" [::mem/int] ::mem/void)

; void ClearWindowState(unsigned int flags);
(ffi/defcfn clear-window-state!
  "Clear window configuration state flags."
  {:arglists '([flags])}
  "ClearWindowState" [::mem/int] ::mem/void)

; void ToggleFullscreen(void);
(ffi/defcfn toggle-fullscreen!
  "Toggle window state: fullscreen/windowed (only PLATFORM_DESKTOP)."
  "ToggleFullscreen" [] ::mem/void)

; void MaximizeWindow(void);
(ffi/defcfn maximize-window!
  "Set window state: maximized, if resizable (only PLATFORM_DESKTOP)."
  "MaximizeWindow" [] ::mem/void)

; void MinimizeWindow(void);
(ffi/defcfn minimize-window!
  "Set window state: minimized, if resizable (only PLATFORM_DESKTOP)."
  "MinimizeWindow" [] ::mem/void)

; void RestoreWindow(void);
(ffi/defcfn restore-window!
  "Set window state: not minimized/maximized (only PLATFORM_DESKTOP)."
  "RestoreWindow" [] ::mem/void)

; void SetWindowIcon(Image image);
(ffi/defcfn set-window-icon!
  "Set icon for window (single image, RGBA 32bit, only PLATFORM_DESKTOP)."
  "SetWindowIcon" [::image] ::mem/void)
         
         

; void SetWindowIcons(Image *images, int count);
#_(ffi/defcfn set-window-icons!
  "Set icon for window (multiple images, RGBA 32bit, only PLATFORM_DESKTOP)."
  "SetWindowIcons" [#_(comment "Array of image types") ::mem/int] ::mem/void)
         

; void SetWindowTitle(const char *title);
(ffi/defcfn set-window-title!
  "Set title for window (only PLATFORM_DESKTOP)"
  {:arglists '([title])}
  "SetWindowTitle" [::mem/c-string] ::mem/void)

; void SetWindowPosition(int x, int y);
(ffi/defcfn set-window-position!
  "Set window position on screen (only PLATFORM_DESKTOP)."
  {:arglists '([x y])}
  "SetWindowPosition" [::mem/int ::mem/int] ::mem/void)

; void SetWindowMonitor(int monitor);
(ffi/defcfn set-window-monitor!
  "Set monitor for the current window (fullscreen mode)."
  {:arglists '([monitor])}
  "SetWindowMonitor" [::mem/int] ::mem/void)

; void SetWindowMinSize(int width, int height);
(ffi/defcfn set-window-min-size!
  "Set window minimum dimensions (for FLAG_WINDOW_RESIZABLE)."
  {:arglists '([width height])}
  "SetWindowMinSize" [::mem/int ::mem/int] ::mem/void)

; void SetWindowSize(int width, int height);
(ffi/defcfn set-window-size!
  "Set window dimensions."
  {:arglists '([width height])}
  "SetWindowSize" [::mem/int ::mem/int] ::mem/void)

; void SetWindowOpacity(float opacity);
(ffi/defcfn set-window-opacity!
  "Set window opacity [0.0f..1.0f] (only PLATFORM_DESKTOP)."
  {:arglists '([opacity])}
  "SetWindowOpacity" [::mem/float] ::mem/void)

; void *GetWindowHandle(void);
(ffi/defcfn get-window-handle
  "Get native window handle."
  "GetWindowHandle" [] ::mem/void)

; int GetScreenWidth(void);
(ffi/defcfn get-screen-width
  "Get current screen width."
  "GetScreenWidth" [] ::mem/int)

; int GetScreenHeight(void);
(ffi/defcfn get-screen-height
  "Get current screen height."
  "GetScreenHeight" [] ::mem/int)

; int GetRenderWidth(void);
(ffi/defcfn get-render-width
  "Get current render width (it considers HiDPI)."
  "GetRenderWidth" [] ::mem/int)

; int GetRenderHeight(void);
(ffi/defcfn get-render-height
  "Get current render height (it considers HiDPI)."
  "GetRenderHeight" [] ::mem/int)

; int GetMonitorCount(void);
(ffi/defcfn get-monitor-count
  "Get number of connected monitors."
  "GetMonitorCount" [] ::mem/int)

; int GetCurrentMonitor(void);
(ffi/defcfn get-current-monitor
  "Get current connected monitor."
  "GetCurrentMonitor" [] ::mem/int)

; Vector2 GetMonitorPosition(int monitor);
(ffi/defcfn get-monitor-position
  "Get specified monitor position."
  {:arglists '([monitor])}
  "GetMonitorPosition" [::mem/int] ::vector2)

; int GetMonitorWidth(int monitor);
(ffi/defcfn get-monitor-width
  "Get specified monitor width (current video mode used by monitor)."
  {:arglists '([monitor])}
  "GetMonitorWidth" [::mem/int] ::mem/int)

; int GetMonitorHeight(int monitor);
(ffi/defcfn get-monitor-height
  "Get specified monitor height (current video mode used by monitor)."
  {:arglists '([monitor])}
  "GetMonitorHeight" [::mem/int] ::mem/int)

; int GetMonitorPhysicalWidth(int monitor);
(ffi/defcfn get-monitor-physical-width
  "Get specified monitor physical width in millimetres."
  {:arglists '([monitor])}
  "GetMonitorPhysicalWidth" [::mem/int] ::mem/int)

; int GetMonitorPhysicalHeight(int monitor);
(ffi/defcfn get-monitor-physical-height
  "Get specified monitor physical height in millimetres."
  {:arglists '([monitor])}
  "GetMonitorPhysicalHeight" [::mem/int] ::mem/int)

; int GetMonitorRefreshRate(int monitor);
(ffi/defcfn get-monitor-refresh-rate
  "Get specified monitor refresh rate."
  {:arglists '([monitor])}
  "GetMonitorRefreshRate" [::mem/int] ::mem/int)

; Vector2 GetWindowPosition(void);
(ffi/defcfn get-window-position
  "Get window position XY on monitor."
  "GetWindowPosition" [] ::vector2)

; Vector2 GetWindowScaleDPI(void);
(ffi/defcfn get-window-scale-dpi
  "Get window scale DPI factor."
  "GetWindowScaleDPI" [] ::vector2)

; const char *GetMonitorName(int monitor);
(ffi/defcfn get-monitor-name
  "Get the human-readable, UTF-8 encoded name of the primary monitor."
  {:arglists '([monitor])}
  "GetMonitorName" [::mem/int] ::mem/c-string)

; void SetClipboardText(const char *text);
(ffi/defcfn set-clipboard-text!
  "Set clipboard text content."
  "SetClipboardText" [::mem/c-string] ::mem/void)

; const char *GetClipboardText(void);
(ffi/defcfn get-clipboard-text
  "Get clipboard text content."
  "GetClipboardText" [] ::mem/c-string)

; void EnableEventWaiting(void);
(ffi/defcfn enable-event-waiting!
  "Enable waiting for events on EndDrawing(), no automatic event polling."
  "EnableEventWaiting" [] ::mem/void)

; void DisableEventWaiting(void);
(ffi/defcfn disable-event-waiting!
  "Disable waiting for events on EndDrawing(), automatic events polling."
  "DisableEventWaiting" [] ::mem/void)

(ffi/defcfn load-image
  ""
  "LoadImage" [::mem/c-string] ::image)