(ns raylib-clj.core
  (:require [coffi.ffi :as ffi :refer [defcfn]]
            [coffi.mem :as mem :refer [defalias]]))

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
(defcfn init-window
  "Initialize window and OpenGL context."
  {:arglists '([width height title])}
  "InitWindow" [::mem/int ::mem/int ::mem/c-string] ::mem/void)

; bool WindowShouldClose(void);
(defcfn window-should-close?
  "Check if KEY_ESCAPE pressed or Close icon pressed."
  "WindowShouldClose" [] ::mem/int)

; void CloseWindow(void);
(defcfn close-window
  "Close window and unload OpenGL context."
  "CloseWindow" [] ::mem/void)

; bool IsWindowReady(void);
(defcfn window-ready?
  "Check if window has been initialized successfully."
  "IsWindowReady" [] ::mem/int)

; bool IsWindowFullscreen(void);
(defcfn window-fullscreen?
  "Check if window is currently fullscreen."
  "IsWindowFullscreen" [] ::mem/int)

; bool IsWindowHidden(void);
(defcfn window-hidden?
  "Check if window is currently hidden (only PLATFORM_DESKTOP)."
  "IsWindowHidden" [] ::mem/int)

; bool IsWindowMinimized(void); 
(defcfn window-minimized?
  "Check if window is currently minimized (only PLATFORM_DESKTOP)."
  "IsWindowMinimized" [] ::mem/int)

; bool IsWindowMaximized(void);
(defcfn window-maximized?
  "Check if window is currently maximized (only PLATFORM_DESKTOP)."
  "IsWindowMaximized" [] ::mem/int)

; bool IsWindowFocused(void); 
(defcfn window-focused?
  "Check if window is currently focused (only PLATFORM_DESKTOP)."
  "IsWindowFocused" [] ::mem/int)

; bool IsWindowResized(void);
(defcfn window-resized?
  "Check if window has been resized last frame."
  "IsWindowResized" [] ::mem/int)

; bool IsWindowState(unsigned int flag);
(defcfn window-state?
  "Check if one specific window flag is enabled."
  {:arglists '([flag])}
  "IsWindowState" [] ::mem/int)

; void SetWindowState(unsigned int flags);
(defcfn set-window-state
  "Set window configuration state using flags (only PLATFORM_DESKTOP)."
  {:arglists '([flags])}
  "SetWindowState" [::mem/int] ::mem/void)

; void ClearWindowState(unsigned int flags);
(defcfn clear-window-state
  "Clear window configuration state flags."
  {:arglists '([flags])}
  "ClearWindowState" [::mem/int] ::mem/void)

; void ToggleFullscreen(void);
(defcfn toggle-fullscreen
  "Toggle window state: fullscreen/windowed (only PLATFORM_DESKTOP)."
  "ToggleFullscreen" [] ::mem/void)

; void MaximizeWindow(void);
(defcfn maximize-window
  "Set window state: maximized, if resizable (only PLATFORM_DESKTOP)."
  "MaximizeWindow" [] ::mem/void)

; void MinimizeWindow(void);
(defcfn minimize-window
  "Set window state: minimized, if resizable (only PLATFORM_DESKTOP)."
  "MinimizeWindow" [] ::mem/void)

; void RestoreWindow(void);
(defcfn restore-window
  "Set window state: not minimized/maximized (only PLATFORM_DESKTOP)."
  "RestoreWindow" [] ::mem/void)

; void SetWindowIcon(Image image);
(defcfn set-window-icon
  "Set icon for window (single image, RGBA 32bit, only PLATFORM_DESKTOP)."
  "SetWindowIcon" [::image] ::mem/void)

; void SetWindowIcons(Image *images, int count);
#_(ffi/defcfn set-window-icons!
    "Set icon for window (multiple images, RGBA 32bit, only PLATFORM_DESKTOP)."
    "SetWindowIcons" [[::mem/array ::image]::mem/int] ::mem/void)


; void SetWindowTitle(const char *title);
(defcfn set-window-title
  "Set title for window (only PLATFORM_DESKTOP)"
  {:arglists '([title])}
  "SetWindowTitle" [::mem/c-string] ::mem/void)

; void SetWindowPosition(int x, int y);
(defcfn set-window-position
  "Set window position on screen (only PLATFORM_DESKTOP)."
  {:arglists '([x y])}
  "SetWindowPosition" [::mem/int ::mem/int] ::mem/void)

; void SetWindowMonitor(int monitor);
(defcfn set-window-monitor
  "Set monitor for the current window (fullscreen mode)."
  {:arglists '([monitor])}
  "SetWindowMonitor" [::mem/int] ::mem/void)

; void SetWindowMinSize(int width, int height);
(defcfn set-window-min-size
  "Set window minimum dimensions (for FLAG_WINDOW_RESIZABLE)."
  {:arglists '([width height])}
  "SetWindowMinSize" [::mem/int ::mem/int] ::mem/void)

; void SetWindowSize(int width, int height);
(defcfn set-window-size
  "Set window dimensions."
  {:arglists '([width height])}
  "SetWindowSize" [::mem/int ::mem/int] ::mem/void)

; void SetWindowOpacity(float opacity);
(defcfn set-window-opacity
  "Set window opacity [0.0f..1.0f] (only PLATFORM_DESKTOP)."
  {:arglists '([opacity])}
  "SetWindowOpacity" [::mem/float] ::mem/void)

; void *GetWindowHandle(void);
(defcfn get-window-handle
  "Get native window handle."
  "GetWindowHandle" [] ::mem/void)

; int GetScreenWidth(void);
(defcfn get-screen-width
  "Get current screen width."
  "GetScreenWidth" [] ::mem/int)

; int GetScreenHeight(void);
(defcfn get-screen-height
  "Get current screen height."
  "GetScreenHeight" [] ::mem/int)

; int GetRenderWidth(void);
(defcfn get-render-width
  "Get current render width (it considers HiDPI)."
  "GetRenderWidth" [] ::mem/int)

; int GetRenderHeight(void);
(defcfn get-render-height
  "Get current render height (it considers HiDPI)."
  "GetRenderHeight" [] ::mem/int)

; int GetMonitorCount(void);
(defcfn get-monitor-count
  "Get number of connected monitors."
  "GetMonitorCount" [] ::mem/int)

; int GetCurrentMonitor(void);
(defcfn get-current-monitor
  "Get current connected monitor."
  "GetCurrentMonitor" [] ::mem/int)

; Vector2 GetMonitorPosition(int monitor);
(defcfn get-monitor-position
  "Get specified monitor position."
  {:arglists '([monitor])}
  "GetMonitorPosition" [::mem/int] ::vector2)

; int GetMonitorWidth(int monitor);
(defcfn get-monitor-width
  "Get specified monitor width (current video mode used by monitor)."
  {:arglists '([monitor])}
  "GetMonitorWidth" [::mem/int] ::mem/int)

; int GetMonitorHeight(int monitor);
(defcfn get-monitor-height
  "Get specified monitor height (current video mode used by monitor)."
  {:arglists '([monitor])}
  "GetMonitorHeight" [::mem/int] ::mem/int)

; int GetMonitorPhysicalWidth(int monitor);
(defcfn get-monitor-physical-width
  "Get specified monitor physical width in millimetres."
  {:arglists '([monitor])}
  "GetMonitorPhysicalWidth" [::mem/int] ::mem/int)

; int GetMonitorPhysicalHeight(int monitor);
(defcfn get-monitor-physical-height
  "Get specified monitor physical height in millimetres."
  {:arglists '([monitor])}
  "GetMonitorPhysicalHeight" [::mem/int] ::mem/int)

; int GetMonitorRefreshRate(int monitor);
(defcfn get-monitor-refresh-rate
  "Get specified monitor refresh rate."
  {:arglists '([monitor])}
  "GetMonitorRefreshRate" [::mem/int] ::mem/int)

; Vector2 GetWindowPosition(void);
(defcfn get-window-position
  "Get window position XY on monitor."
  "GetWindowPosition" [] ::vector2)

; Vector2 GetWindowScaleDPI(void);
(defcfn get-window-scale-dpi
  "Get window scale DPI factor."
  "GetWindowScaleDPI" [] ::vector2)

; const char *GetMonitorName(int monitor);
(defcfn get-monitor-name
  "Get the human-readable, UTF-8 encoded name of the primary monitor."
  {:arglists '([monitor])}
  "GetMonitorName" [::mem/int] ::mem/c-string)

; void SetClipboardText(const char *text);
(defcfn set-clipboard-text!
  "Set clipboard text content."
  "SetClipboardText" [::mem/c-string] ::mem/void)

; const char *GetClipboardText(void);
(defcfn get-clipboard-text
  "Get clipboard text content."
  "GetClipboardText" [] ::mem/c-string)

; void EnableEventWaiting(void);
(defcfn enable-event-waiting
  "Enable waiting for events on EndDrawing(), no automatic event polling."
  "EnableEventWaiting" [] ::mem/void)

; void DisableEventWaiting(void);
(defcfn disable-event-waiting
  "Disable waiting for events on EndDrawing(), automatic events polling."
  "DisableEventWaiting" [] ::mem/void)


; -----------------------------------------------------------------------------
;; Cursor-related functions
; -----------------------------------------------------------------------------

; void ShowCursor(void);
(defcfn show-cursor
  "Shows cursor"
  "ShowCursor" [] ::mem/void)

; void HideCursor(void);
(defcfn hide-cursor
  "Hides cursor"
  "HideCursor" [] ::mem/void)

; bool IsCursorHidden(void);
(defcfn cursor-hidden?
  "Check if cursor is not visible"
  "IsCursorHidden" [] ::mem/void)

; void EnableCursor(void);
(defcfn enable-cursor
  "Enables cursor (unlock cursor)"
  "EnableCursor" [] ::mem/void)

; void DisableCursor(void);
(defcfn disable-cursor
  "Disables cursor (lock cursor)"
  "DisableCursor" [] ::mem/void)

; bool IsCursorOnScreen(void);
(defcfn cursor-on-screen?
  "Check if cursor is on the screen"
  "IsCursorOnScreen" [] ::mem/int)

; -----------------------------------------------------------------------------
;; Drawing-related functions
; -----------------------------------------------------------------------------

; void ClearBackground(Color color);
(defcfn clear-background
  "Set background color (framebuffer clear color)"
  "ClearBackground" [#_::color] ::mem/void)

; void BeginDrawing(void);
(defcfn begin-drawing
  "Setup canvas (framebuffer) to start drawing"
  "BeginDrawing" [] ::mem/void)

; void EndDrawing(void);
(defcfn end-drawing
  "End canvas drawing and swap buffers (double buffering)"
  "EndDrawing" [] ::mem/void)

; void BeginMode2D(Camera2D camera);
(defcfn begin-mode-2d
  "Begin 2D mode with custom camera (2D)"
  "BeginMode2D" [#_::camera-2d] ::mem/void)

; void EndMode2D(void);
(defcfn end-mode-2d
  "Ends 2D mode with custom camera"
  "EndMode2D" [] ::mem/void)

; void BeginMode3D(Camera3D camera);
(defcfn begin-mode-3d
  "Begin 3D mode with custom camera (3D)"
  "BeginMode3D" [#_::camera-3d] ::mem/void)

; void EndMode3D(void);
(defcfn end-mode-3d
  "Ends 3D mode and returns to default 2D orthographic mode"
  "EndMode3D" [] ::mem/void)

; void BeginTextureMode(RenderTexture2D target);
(defcfn begin-texture-mode
  "Begin drawing to render texture"
  "BeginTextureMode" [#_(comment ::render-texture-2d)] ::mem/void)

; void EndTextureMode(void);
(defcfn end-texture-mode
  "Ends drawing to render texture"
  "EndTextureMode" [] ::mem/void)

; void BeginShaderMode(Shader shader);
(defcfn begin-shader-mode
  "Begin custom shader drawing"
  "BeginShaderMode" [#_::shader] ::mem/void)

; void EndShaderMode(void);
(defcfn end-shader-mode
  "End custom shader drawing (use default shader)"
  "EndShaderMode" [] ::mem/void)

; void BeginBlendMode(int mode);
(defcfn begin-blend-mode
  "Begin blending mode (alpha, additive, multiplied, subtract, custom)"
  "BeginBlendMode" [::mem/int] ::mem/void)

; void EndBlendMode(void);
(defcfn end-blend-mode
  "End blending mode (reset to default: alpha blending)"
  "EndBlendMode" [] ::mem/void)

; void BeginScissorMode(int x, int y, int width, int height); // 
(defcfn begin-scissor-mode
  "Begin scissor mode (define screen area for following drawing)"
  "BeginScissorMode" [::mem/int ::mem/int ::mem/int ::mem/int] ::mem/void)

; void EndScissorMode(void);
(defcfn end-scissor-mode
  "End scissor mode"
  "EndScissorMode" [] ::mem/void)

; void BeginVrStereoMode(VrStereoConfig config);
(defcfn begin-vr-stereo-mode
  "Begin stereo rendering (requires VR simulator)"
  "BeginVrStereoMode" [#_::vr-stereo-config] ::mem/void)

; void EndVrStereoMode(void);
(defcfn end-vr-stereo-mode
  "End stereo rendering (requires VR simulator)"
  "EndVrStereoMode" [] ::mem/void)