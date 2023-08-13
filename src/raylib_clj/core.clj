(ns raylib-clj.core
  (:require
   [coffi.ffi :as ffi :refer [defcfn]]
   [coffi.mem :as mem :refer [defalias defstructs]]
   [coffi.layout :as layout]))

(ffi/load-system-library "raylib")

;; -----------------------------------------------------------------------------
;; Helper functions from https://github.com/IGJoshua/glfw-clj
;; glwfw-clj/core.clj
;; -----------------------------------------------------------------------------
(defmethod mem/primitive-type ::bool
  [_type]
  ::mem/int)

(defmethod mem/serialize* ::bool
  [obj _type _scope]
  (int (if obj 1 0)))

(defmethod mem/deserialize* ::bool
  [obj _type]
  (not (zero? obj)))
;; -----------------------------------------------------------------------------

(defmacro defstruct
  [new-type aliased-types]
  `(defalias ~new-type [:coffi.mem/struct ~aliased-types]))

(defstruct ::vector2
  [[:x ::mem/float]
   [:y ::mem/float]])

(defstruct ::vector3 
  [[:x ::mem/float]
   [:y ::mem/float]
   [:z ::mem/float]])

(defstruct ::vector4 
  [[:x ::mem/float]
   [:y ::mem/float]
   [:z ::mem/float]
   [:w ::mem/float]])

(defalias ::quaternion ::vector4)

; Matrix, 4x4 components, column major, OpenGL style, right-handed.
(defstruct ::matrix 
  [[:m0 ::mem/float] [:m4 ::mem/float] [:m8  ::mem/float] [:m12 ::mem/float]
   [:m1 ::mem/float] [:m5 ::mem/float] [:m9  ::mem/float] [:m13 ::mem/float]
   [:m2 ::mem/float] [:m6 ::mem/float] [:m10 ::mem/float] [:m14 ::mem/float]
   [:m3 ::mem/float] [:m7 ::mem/float] [:m11 ::mem/float] [:m15 ::mem/float]])

; Color, 4 components, R8G8B8A8 (32bit).
(defstruct ::color 
  [[:r ::mem/char]
   [:g ::mem/char]
   [:b ::mem/char]
   [:a ::mem/char]])

(defstruct ::rectangle 
  [[:x      ::mem/float]
   [:y      ::mem/float]
   [:width  ::mem/float]
   [:height ::mem/float]])

; Image, pixel data stored in CPU memory (RAM).
(defstruct ::image
  [[:data    ::mem/pointer]
   [:width   ::mem/int]
   [:height  ::mem/int]
   [:mipmaps ::mem/int]
   [:format  ::mem/int]])

; Texture, tex data stored in GPU memory (VRAM).
(defstruct ::texture 
  [[:id      ::mem/int]
   [:width   ::mem/int]
   [:height  ::mem/int]
   [:mipmaps ::mem/int]
   [:format  ::mem/int]])

(defalias ::texture-2d ::texture)
(defalias ::texture-cubemap ::texture)

; RenderTexture, fbo for texture rendering
(defstruct ::render-texture 
  [[:id      ::mem/int]
   [:texture ::texture]
   [:depth   ::texture]])

(defalias ::render-texture-2d ::render-texture)

; NPatchInfo, n-patch layout info
(defstruct ::n-patch-info 
   [[:source ::rectangle]
    [:left   ::mem/int]
    [:top    ::mem/int]
    [:right  ::mem/int]
    [:bottom ::mem/int]
    [:layout ::mem/int]])

; GlyphInfo, font characters glyphs info 
(defstruct ::glyph-info
  [[:value     ::mem/int]
   [:offset-x  ::mem/int]
   [:offset-y  ::mem/int]
   [:advance-x ::mem/int]
   [:image     ::image]])

; Font, font texture and GlyphInfo array data
(defstruct ::font
  [[:base-size     ::mem/int]
   [:glyph-count   ::mem/int]
   [:glyph-padding ::mem/int]
   [:texture       ::texture-2d]
   [:recs          ::mem/pointer]
   [:glyphs        ::mem/pointer]])

(defstruct ::camera-2d 
  [[:offset   ::vector2]
   [:target   ::vector2]
   [:rotation ::mem/float]
   [:zoom     ::mem/float]])

(defstruct ::camera-3d 
  [[:position   ::vector3]
   [:target     ::vector3]
   [:up         ::vector3]
   [:fovy       ::mem/float]
   [:projection ::mem/int]])

(defalias ::camera ::camera-3d)

(comment (defstruct ::mesh
           [
   ; number of vertices stored in arrays
            [:vertex-count   ::mem/int]

   ; number of triangles stored (indexed or not)
            [:triangle-count ::mem/int]

   ; Vertex position (XYZ - 3 components per vertex) (shader-location = 0)
            [:vertices       ::mem/pointer]

   ; Vertex texture coordinates (UV - 2 components per vertex) 
   ; (shader-location = 1)
            [:tex-coords     ::mem/pointer]

   ; Vertex texture second coordinates (UV - 2 components per vertex) 
   ; (shader-location = 5)
            [:tex-coords2    ::mem/pointer]

   ; Vertex normals (XYZ - 3 components per vertex) (shader-location = 2)
            [:normals        ::mem/pointer]

   ; Vertex tangents (XYZW - 4 components per vertex) (shader-location = 4)
            [:tangents       ::mem/pointer]

   ; Vertex colors (RGBA - 4 components per vertex) (shader-location = 3)
            [:colors         ::mem/pointer]

   ; Vertex indices (in case vertex data comes indexed)
            [:indices        ::mem/pointer]

   ; Animated vertex positions (after bones transformations)
            [:anim-vertices  ::mem/pointer]

   ; Animated normals (after bones transformations)
            [:anim-normals   ::mem/pointer]

   ; Vertex bone ids, max 255 bone ids, up to 4 bones influence by vertex 
   ; (skinning)
            [:bone-ids       ::mem/pointer]

   ; Vertex bone weight, up to 4 bones influence by vertex (skinning)
            [:bone-weights   ::mem/pointer]
            
   ; OpenGL Vertex Array Object id
            [:vao-id         ::mem/int]

   ; OpenGL Vertex Buffer Objects id (default vertex data)
            [:vbo-id         ::mem/pointer]])
         )

(defstruct ::mesh
  [[:vertex-count   ::mem/int]
   [:triangle-count ::mem/int]
   [:vertices       ::mem/pointer] ; float *
   [:tex-coords     ::mem/pointer] ; float *
   [:tex-coords2    ::mem/pointer] ; float * 
   [:normals        ::mem/pointer] ; float *
   [:tangents       ::mem/pointer] ; float *
   [:colors         ::mem/pointer] ; unsigned char *
   [:indices        ::mem/pointer] ; unsigned short *
   [:anim-vertices  ::mem/pointer] ; float *
   [:anim-normals   ::mem/pointer] ; float *
   [:bone-ids       ::mem/pointer] ; unsigned char *
   [:bone-weights   ::mem/pointer] ; float *
   [:vao-id         ::mem/int]
   [:vbo-id         ::mem/pointer] ; unsigned int *
   ])

(defstruct ::shader 
  [[:id   ::mem/int]
   [:locs ::mem/pointer] ; int *
   ])

(defstruct ::material-map
  [[:texture ::texture-2d]
   [:color   ::color]
   [:value   ::mem/float]])

(defstruct ::material
  [[:shader ::shader]
   [:maps   ::mem/pointer] ; MaterialMap *
   [:params [::mem/array ::mem/float 4]]])

(defstruct ::transform
  [[:translation ::vector3]
   [:rotation    ::quaternion]
   [:scale       ::vector3]])

(defstruct ::bone-info
  [[:name   [::mem/array ::mem/char 32]]
   [:parent ::mem/int]])

(defstruct ::model
  [[:transform ::matrix]
   [:mesh-count ::mem/int]
   [:matrerial-count ::mem/int]
   [:meshes ::mem/pointer] ; Mesh *
   [:materials ::mem/pointer] ; Material *
   [:mesh-materials ::mem/pointer] ; int *
   [:bone-count ::mem/int]
   [:bones ::mem/pointer] ; BoneInfo *
   [:bind-pose ::mem/pointer] ; Transform *
   ])

(defstruct ::model-animation
  [[:bone-count        ::mem/int]
   [:frame-count       ::mem/int]
   [:bones             ::mem/pointer] ; BoneInfo *
   [:frame-poses       ::mem/pointer] ; Transform **
   [:name [::mem/array ::mem/char 32]]])

(defstruct ::ray
  [[:position  ::vector3]
   [:direction ::vector3]])

(defstruct ::ray-collision
  [[:hit ::bool]
   [:distance ::mem/float]
   [:point ::vector3]
   [:normal ::vector3]])

(defstruct ::bounding-box
  [[:min ::vector3]
   [:max ::vector3]])

(defstruct ::wave
  [[:frame-count ::mem/int]
   [:sample-rate ::mem/int]
   [:sample-size ::mem/int]
   [:channels    ::mem/int]
   [:data        ::mem/pointer] ; void *
   ])

;;------------------------------------------------------------------------------
;; Raylib Color Definitions
;; "Custom raylib color palette for amazing visuals on WHITE background "
;;------------------------------------------------------------------------------

(def ^:const ::light-gray {:r 200 :g 200 :b 200 :a 255})
(def ^:const ::gray {:r 80 :g 80 :b 80 :a 255})
(def ^:const ::dark-gray {:r 200 :g 200 :b 200 :a 255})
(def ^:const ::yellow {:r 253 :g 249 :b 0 :a 255})
(def ^:const ::gold {:r 255 :g 203 :b 0 :a 255})
(def ^:const ::orange {:r 255 :g 161 :b 0 :a 255})
(def ^:const ::pink {:r 255 :g 109 :b 194 :a 255})
(def ^:const ::red {:r 230 :g 41 :b 55 :a 255})
(def ^:const ::maroon {:r 190 :g 33 :b 55 :a 255})
(def ^:const ::green {:r 0 :g 228 :b 48 :a 255})
(def ^:const ::lime {:r 0 :g 158 :b 47 :a 255})
(def ^:const ::dark-green {:r 0 :g 117 :b 44 :a 255})
(def ^:const ::sky-blue {:r 102 :g 191 :b 255 :a 255})
(def ^:const ::blue {:r 0 :g 200 :b 241 :a 255})
(def ^:const ::dark-blue {:r 0 :g 82 :b 172 :a 255})
(def ^:const ::purple {:r 200 :g 122 :b 255 :a 255})
(def ^:const ::violet {:r 135 :g 60 :b 190 :a 255})
(def ^:const ::dark-purple {:r 112 :g 31 :b 126 :a 255})
(def ^:const ::beige {:r 211 :g 176 :b 131 :a 255})
(def ^:const ::brown {:r 127 :g 106 :b 79 :a 255})
(def ^:const ::dark-brown {:r 76 :g 63 :b 47 :a 255})
(def ^:const ::white {:r 255 :g 255 :b 255 :a 255})
(def ^:const ::black {:r 0 :g 0 :b 0 :a 255})
(def ^:const ::blank {:r 0 :g 0 :b 0 :a 0})
(def ^:const ::magenta {:r 255 :g 0 :b 255 :a 255})
(def ^:const ::ray-white {:r 245 :g 245 :b 245 :a 255})

;;------------------------------------------------------------------------------
;; Window-related Functions
;;------------------------------------------------------------------------------

; void InitWindow(int width, int height, const char *title);  
(defcfn init-window!
  "Initialize window and OpenGL context."
  {:arglists '([width height title])}
  "InitWindow" [::mem/int ::mem/int ::mem/c-string] ::mem/void)

; bool WindowShouldClose(void);
(defcfn window-should-close?
  "Check if KEY_ESCAPE pressed or Close icon pressed."
  "WindowShouldClose" [] ::bool)

; void CloseWindow(void);
(defcfn close-window
  "Close window and unload OpenGL context."
  "CloseWindow" [] ::mem/void)

; bool IsWindowReady(void);
(defcfn window-ready?
  "Check if window has been initialized successfully."
  "IsWindowReady" [] ::bool)

; bool IsWindowFullscreen(void);
(defcfn window-fullscreen?
  "Check if window is currently fullscreen."
  "IsWindowFullscreen" [] ::bool)

; bool IsWindowHidden(void);
(defcfn window-hidden?
  "Check if window is currently hidden (only PLATFORM_DESKTOP)."
  "IsWindowHidden" [] ::bool)

; bool IsWindowMinimized(void); 
(defcfn window-minimized?
  "Check if window is currently minimized (only PLATFORM_DESKTOP)."
  "IsWindowMinimized" [] ::bool)

; bool IsWindowMaximized(void);
(defcfn window-maximized?
  "Check if window is currently maximized (only PLATFORM_DESKTOP)."
  "IsWindowMaximized" [] ::bool)

; bool IsWindowFocused(void); 
(defcfn window-focused?
  "Check if window is currently focused (only PLATFORM_DESKTOP)."
  "IsWindowFocused" [] ::bool)

; bool IsWindowResized(void);
(defcfn window-resized?
  "Check if window has been resized last frame."
  "IsWindowResized" [] ::bool)

; bool IsWindowState(unsigned int flag);
(defcfn window-state?
  "Check if one specific window flag is enabled."
  {:arglists '([flag])}
  "IsWindowState" [::mem/int] ::bool)

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
  {:arglists '([image])}
  "SetWindowIcon" [::image] ::mem/void)

; void SetWindowIcons(Image *images, int count);
(ffi/defcfn set-window-icons
  "Set icon for window (multiple images, RGBA 32bit, only PLATFORM_DESKTOP)."
  "SetWindowIcons" [::mem/pointer ::mem/int] ::mem/void)


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
(defcfn get-monitor-refresh-rate>
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
  {:arglists '([text])}
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


;;------------------------------------------------------------------------------
;; Cursor-related functions
;;------------------------------------------------------------------------------

; void ShowCursor(void);
(defcfn show-cursor
  "Shows cursor."
  "ShowCursor" [] ::mem/void)

; void HideCursor(void);
(defcfn hide-cursor
  "Hides cursor."
  "HideCursor" [] ::mem/void)

; bool IsCursorHidden(void);
(defcfn cursor-hidden?
  "Check if cursor is not visible."
  "IsCursorHidden" [] ::bool)

; void EnableCursor(void);
(defcfn enable-cursor
  "Enables cursor (unlock cursor)."
  "EnableCursor" [] ::mem/void)

; void DisableCursor(void);
(defcfn disable-cursor
  "Disables cursor (lock cursor)."
  "DisableCursor" [] ::mem/void)

; bool IsCursorOnScreen(void);
(defcfn cursor-on-screen?
  "Check if cursor is on the screen."
  "IsCursorOnScreen" [] ::bool)

;;------------------------------------------------------------------------------
;; Drawing-related functions
;;------------------------------------------------------------------------------

; void ClearBackground(Color color);
(defcfn clear-background
  "Set background color (framebuffer clear color)."
  {:arglists '([color])}
  "ClearBackground" [::color] ::mem/void)

; void BeginDrawing(void);
(defcfn begin-drawing
  "Setup canvas (framebuffer) to start drawing."
  "BeginDrawing" [] ::mem/void)

; void EndDrawing(void);
(defcfn end-drawing
  "End canvas drawing and swap buffers (double buffering)."
  "EndDrawing" [] ::mem/void)

; void BeginMode2D(Camera2D camera);
(defcfn begin-mode-2d
  "Begin 2D mode with custom camera (2D)."
  {:arglists '([camera])}
  "BeginMode2D" [::camera-2d] ::mem/void)

; void EndMode2D(void);
(defcfn end-mode-2d
  "Ends 2D mode with custom camera."
  "EndMode2D" [] ::mem/void)

; void BeginMode3D(Camera3D camera);
(defcfn begin-mode-3d
  "Begin 3D mode with custom camera (3D)."
  {:arglists '([camera])}
  "BeginMode3D" [::camera-3d] ::mem/void)

; void EndMode3D(void);
(defcfn end-mode-3d
  "Ends 3D mode and returns to default 2D orthographic mode."
  "EndMode3D" [] ::mem/void)

; void BeginTextureMode(RenderTexture2D target);
(defcfn begin-texture-mode
  "Begin drawing to render texture."
  {:arglists '([target])}
  "BeginTextureMode" [#_(comment ::render-texture-2d)] ::mem/void)

; void EndTextureMode(void);
(defcfn end-texture-mode
  "Ends drawing to render texture."
  "EndTextureMode" [] ::mem/void)

; void BeginShaderMode(Shader shader);
(defcfn begin-shader-mode
  "Begin custom shader drawing."
  {:arglists '([shader])}
  "BeginShaderMode" [::shader] ::mem/void)

; void EndShaderMode(void);
(defcfn end-shader-mode
  "End custom shader drawing (use default shader)."
  "EndShaderMode" [] ::mem/void)

; void BeginBlendMode(int mode);
(defcfn begin-blend-mode
  "Begin blending mode (alpha, additive, multiplied, subtract, custom)."
  {:arglists '([mode])}
  "BeginBlendMode" [::mem/int] ::mem/void)

; void EndBlendMode(void);
(defcfn end-blend-mode
  "End blending mode (reset to default: alpha blending)."
  "EndBlendMode" [] ::mem/void)

; void BeginScissorMode(int x, int y, int width, int height); // 
(defcfn begin-scissor-mode
  "Begin scissor mode (define screen area for following drawing)."
  {:arglists '([x y width height])}
  "BeginScissorMode" [::mem/int ::mem/int ::mem/int ::mem/int] ::mem/void)

; void EndScissorMode(void);
(defcfn end-scissor-mode
  "End scissor mode."
  "EndScissorMode" [] ::mem/void)

; void BeginVrStereoMode(VrStereoConfig config);
(defcfn begin-vr-stereo-mode
  "Begin stereo rendering (requires VR simulator)."
  {:arglists '([config])}
  "BeginVrStereoMode" [#_::vr-stereo-config] ::mem/void)

; void EndVrStereoMode(void);
(defcfn end-vr-stereo-mode
  "End stereo rendering (requires VR simulator)."
  "EndVrStereoMode" [] ::mem/void)
