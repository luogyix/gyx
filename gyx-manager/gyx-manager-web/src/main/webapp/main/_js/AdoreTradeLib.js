define("ts/hotkey/Keys", ["require", "exports"], function (require, exports) {
    "use strict";
    (function (Keys) {
        Keys[Keys["BACKSPACE"] = 8] = "BACKSPACE";
        Keys[Keys["TAB"] = 9] = "TAB";
        Keys[Keys["CLEAR"] = 12] = "CLEAR";
        Keys[Keys["ENTER"] = 13] = "ENTER";
        Keys[Keys["SHIFT"] = 16] = "SHIFT";
        Keys[Keys["CTRL"] = 17] = "CTRL";
        Keys[Keys["ALT"] = 18] = "ALT";
        Keys[Keys["META"] = 91] = "META";
        Keys[Keys["PAUSE"] = 19] = "PAUSE";
        Keys[Keys["CAPS_LOCK"] = 20] = "CAPS_LOCK";
        Keys[Keys["ESCAPE"] = 27] = "ESCAPE";
        Keys[Keys["SPACE"] = 32] = "SPACE";
        Keys[Keys["PAGE_UP"] = 33] = "PAGE_UP";
        Keys[Keys["PAGE_DOWN"] = 34] = "PAGE_DOWN";
        Keys[Keys["END"] = 35] = "END";
        Keys[Keys["HOME"] = 36] = "HOME";
        Keys[Keys["LEFT_ARROW"] = 37] = "LEFT_ARROW";
        Keys[Keys["UP_ARROW"] = 38] = "UP_ARROW";
        Keys[Keys["RIGHT_ARROW"] = 39] = "RIGHT_ARROW";
        Keys[Keys["DOWN_ARROW"] = 40] = "DOWN_ARROW";
        Keys[Keys["INSERT"] = 45] = "INSERT";
        Keys[Keys["DELETE"] = 46] = "DELETE";
        Keys[Keys["HELP"] = 47] = "HELP";
        Keys[Keys["NUM_0"] = 48] = "NUM_0";
        Keys[Keys["NUM_1"] = 49] = "NUM_1";
        Keys[Keys["NUM_2"] = 50] = "NUM_2";
        Keys[Keys["NUM_3"] = 51] = "NUM_3";
        Keys[Keys["NUM_4"] = 52] = "NUM_4";
        Keys[Keys["NUM_5"] = 53] = "NUM_5";
        Keys[Keys["NUM_6"] = 54] = "NUM_6";
        Keys[Keys["NUM_7"] = 55] = "NUM_7";
        Keys[Keys["NUM_8"] = 56] = "NUM_8";
        Keys[Keys["NUM_9"] = 57] = "NUM_9";
        Keys[Keys["CHAR_A"] = 65] = "CHAR_A";
        Keys[Keys["CHAR_B"] = 66] = "CHAR_B";
        Keys[Keys["CHAR_C"] = 67] = "CHAR_C";
        Keys[Keys["CHAR_D"] = 68] = "CHAR_D";
        Keys[Keys["CHAR_E"] = 69] = "CHAR_E";
        Keys[Keys["CHAR_F"] = 70] = "CHAR_F";
        Keys[Keys["CHAR_G"] = 71] = "CHAR_G";
        Keys[Keys["CHAR_H"] = 72] = "CHAR_H";
        Keys[Keys["CHAR_I"] = 73] = "CHAR_I";
        Keys[Keys["CHAR_J"] = 74] = "CHAR_J";
        Keys[Keys["CHAR_K"] = 75] = "CHAR_K";
        Keys[Keys["CHAR_L"] = 76] = "CHAR_L";
        Keys[Keys["CHAR_M"] = 77] = "CHAR_M";
        Keys[Keys["CHAR_N"] = 78] = "CHAR_N";
        Keys[Keys["CHAR_O"] = 79] = "CHAR_O";
        Keys[Keys["CHAR_P"] = 80] = "CHAR_P";
        Keys[Keys["CHAR_Q"] = 81] = "CHAR_Q";
        Keys[Keys["CHAR_R"] = 82] = "CHAR_R";
        Keys[Keys["CHAR_S"] = 83] = "CHAR_S";
        Keys[Keys["CHAR_T"] = 84] = "CHAR_T";
        Keys[Keys["CHAR_U"] = 85] = "CHAR_U";
        Keys[Keys["CHAR_V"] = 86] = "CHAR_V";
        Keys[Keys["CHAR_W"] = 87] = "CHAR_W";
        Keys[Keys["CHAR_X"] = 88] = "CHAR_X";
        Keys[Keys["CHAR_Y"] = 89] = "CHAR_Y";
        Keys[Keys["CHAR_Z"] = 90] = "CHAR_Z";
        Keys[Keys["CHAR_A_LOWERCASE"] = 97] = "CHAR_A_LOWERCASE";
        Keys[Keys["CHAR_B_LOWERCASE"] = 98] = "CHAR_B_LOWERCASE";
        Keys[Keys["CHAR_C_LOWERCASE"] = 99] = "CHAR_C_LOWERCASE";
        Keys[Keys["CHAR_D_LOWERCASE"] = 100] = "CHAR_D_LOWERCASE";
        Keys[Keys["CHAR_E_LOWERCASE"] = 101] = "CHAR_E_LOWERCASE";
        Keys[Keys["CHAR_F_LOWERCASE"] = 102] = "CHAR_F_LOWERCASE";
        Keys[Keys["CHAR_G_LOWERCASE"] = 103] = "CHAR_G_LOWERCASE";
        Keys[Keys["CHAR_H_LOWERCASE"] = 104] = "CHAR_H_LOWERCASE";
        Keys[Keys["CHAR_I_LOWERCASE"] = 105] = "CHAR_I_LOWERCASE";
        Keys[Keys["CHAR_J_LOWERCASE"] = 106] = "CHAR_J_LOWERCASE";
        Keys[Keys["CHAR_K_LOWERCASE"] = 107] = "CHAR_K_LOWERCASE";
        Keys[Keys["CHAR_L_LOWERCASE"] = 108] = "CHAR_L_LOWERCASE";
        Keys[Keys["CHAR_M_LOWERCASE"] = 109] = "CHAR_M_LOWERCASE";
        Keys[Keys["CHAR_N_LOWERCASE"] = 110] = "CHAR_N_LOWERCASE";
        Keys[Keys["CHAR_O_LOWERCASE"] = 111] = "CHAR_O_LOWERCASE";
        Keys[Keys["CHAR_P_LOWERCASE"] = 112] = "CHAR_P_LOWERCASE";
        Keys[Keys["CHAR_Q_LOWERCASE"] = 113] = "CHAR_Q_LOWERCASE";
        Keys[Keys["CHAR_R_LOWERCASE"] = 114] = "CHAR_R_LOWERCASE";
        Keys[Keys["CHAR_S_LOWERCASE"] = 115] = "CHAR_S_LOWERCASE";
        Keys[Keys["CHAR_T_LOWERCASE"] = 116] = "CHAR_T_LOWERCASE";
        Keys[Keys["CHAR_U_LOWERCASE"] = 117] = "CHAR_U_LOWERCASE";
        Keys[Keys["CHAR_V_LOWERCASE"] = 118] = "CHAR_V_LOWERCASE";
        Keys[Keys["CHAR_W_LOWERCASE"] = 119] = "CHAR_W_LOWERCASE";
        Keys[Keys["CHAR_X_LOWERCASE"] = 120] = "CHAR_X_LOWERCASE";
        Keys[Keys["CHAR_Y_LOWERCASE"] = 121] = "CHAR_Y_LOWERCASE";
        Keys[Keys["CHAR_Z_LOWERCASE"] = 122] = "CHAR_Z_LOWERCASE";
        Keys[Keys["LEFT_WINDOW"] = 91] = "LEFT_WINDOW";
        Keys[Keys["RIGHT_WINDOW"] = 92] = "RIGHT_WINDOW";
        Keys[Keys["SELECT"] = 93] = "SELECT";
        Keys[Keys["NUMPAD_0"] = 96] = "NUMPAD_0";
        Keys[Keys["NUMPAD_1"] = 97] = "NUMPAD_1";
        Keys[Keys["NUMPAD_2"] = 98] = "NUMPAD_2";
        Keys[Keys["NUMPAD_3"] = 99] = "NUMPAD_3";
        Keys[Keys["NUMPAD_4"] = 100] = "NUMPAD_4";
        Keys[Keys["NUMPAD_5"] = 101] = "NUMPAD_5";
        Keys[Keys["NUMPAD_6"] = 102] = "NUMPAD_6";
        Keys[Keys["NUMPAD_7"] = 103] = "NUMPAD_7";
        Keys[Keys["NUMPAD_8"] = 104] = "NUMPAD_8";
        Keys[Keys["NUMPAD_9"] = 105] = "NUMPAD_9";
        Keys[Keys["NUMPAD_MULTIPLY"] = 106] = "NUMPAD_MULTIPLY";
        Keys[Keys["NUMPAD_PLUS"] = 107] = "NUMPAD_PLUS";
        Keys[Keys["NUMPAD_ENTER"] = 108] = "NUMPAD_ENTER";
        Keys[Keys["NUMPAD_MINUS"] = 109] = "NUMPAD_MINUS";
        Keys[Keys["NUMPAD_PERIOD"] = 110] = "NUMPAD_PERIOD";
        Keys[Keys["NUMPAD_DIVIDE"] = 111] = "NUMPAD_DIVIDE";
        Keys[Keys["F1"] = 112] = "F1";
        Keys[Keys["F2"] = 113] = "F2";
        Keys[Keys["F3"] = 114] = "F3";
        Keys[Keys["F4"] = 115] = "F4";
        Keys[Keys["F5"] = 116] = "F5";
        Keys[Keys["F6"] = 117] = "F6";
        Keys[Keys["F7"] = 118] = "F7";
        Keys[Keys["F8"] = 119] = "F8";
        Keys[Keys["F9"] = 120] = "F9";
        Keys[Keys["F10"] = 121] = "F10";
        Keys[Keys["F11"] = 122] = "F11";
        Keys[Keys["F12"] = 123] = "F12";
        Keys[Keys["F13"] = 124] = "F13";
        Keys[Keys["F14"] = 125] = "F14";
        Keys[Keys["F15"] = 126] = "F15";
        Keys[Keys["NUM_LOCK"] = 144] = "NUM_LOCK";
        Keys[Keys["SCROLL_LOCK"] = 145] = "SCROLL_LOCK";
        Keys[Keys["UP_DPAD"] = 175] = "UP_DPAD";
        Keys[Keys["DOWN_DPAD"] = 176] = "DOWN_DPAD";
        Keys[Keys["LEFT_DPAD"] = 177] = "LEFT_DPAD";
        Keys[Keys["RIGHT_DPAD"] = 178] = "RIGHT_DPAD";
        Keys[Keys["SLASH"] = 191] = "SLASH";
    })(exports.Keys || (exports.Keys = {}));
    var Keys = exports.Keys;
});
define("ts/hotkey/KeystrokeGenerator", ["require", "exports", "ts/hotkey/Keys"], function (require, exports, Keys_1) {
    "use strict";
    var KeystrokeGenerator = (function () {
        function KeystrokeGenerator() {
        }
        KeystrokeGenerator.generateKeyStroke = function (e) {
            var stroke = "";
            var ctrlKey = e.ctrlKey;
            stroke = ctrlKey ? stroke + "CTRL+" : stroke;
            var shiftKey = e.shiftKey;
            stroke = shiftKey ? stroke + "SHIFT+" : stroke;
            var altKey = e.altKey;
            stroke = altKey ? stroke + "ALT+" : stroke;
            var handled = false;
            if (e.type === "keypress") {
                handled = true;
                switch (e.keyCode) {
                    case Keys_1.Keys.CHAR_A_LOWERCASE:
                        stroke = stroke + "A";
                        break;
                    case Keys_1.Keys.CHAR_B_LOWERCASE:
                        stroke = stroke + "B";
                        break;
                    case Keys_1.Keys.CHAR_C_LOWERCASE:
                        stroke = stroke + "C";
                        break;
                    case Keys_1.Keys.CHAR_D_LOWERCASE:
                        stroke = stroke + "D";
                        break;
                    case Keys_1.Keys.CHAR_E_LOWERCASE:
                        stroke = stroke + "E";
                        break;
                    case Keys_1.Keys.CHAR_F_LOWERCASE:
                        stroke = stroke + "F";
                        break;
                    case Keys_1.Keys.CHAR_G_LOWERCASE:
                        stroke = stroke + "G";
                        break;
                    case Keys_1.Keys.CHAR_H_LOWERCASE:
                        stroke = stroke + "H";
                        break;
                    case Keys_1.Keys.CHAR_I_LOWERCASE:
                        stroke = stroke + "I";
                        break;
                    case Keys_1.Keys.CHAR_J_LOWERCASE:
                        stroke = stroke + "J";
                        break;
                    case Keys_1.Keys.CHAR_K_LOWERCASE:
                        stroke = stroke + "K";
                        break;
                    case Keys_1.Keys.CHAR_L_LOWERCASE:
                        stroke = stroke + "L";
                        break;
                    case Keys_1.Keys.CHAR_M_LOWERCASE:
                        stroke = stroke + "M";
                        break;
                    case Keys_1.Keys.CHAR_N_LOWERCASE:
                        stroke = stroke + "N";
                        break;
                    case Keys_1.Keys.CHAR_O_LOWERCASE:
                        stroke = stroke + "O";
                        break;
                    case Keys_1.Keys.CHAR_P_LOWERCASE:
                        stroke = stroke + "P";
                        break;
                    case Keys_1.Keys.CHAR_Q_LOWERCASE:
                        stroke = stroke + "Q";
                        break;
                    case Keys_1.Keys.CHAR_R_LOWERCASE:
                        stroke = stroke + "R";
                        break;
                    case Keys_1.Keys.CHAR_S_LOWERCASE:
                        stroke = stroke + "S";
                        break;
                    case Keys_1.Keys.CHAR_T_LOWERCASE:
                        stroke = stroke + "T";
                        break;
                    case Keys_1.Keys.CHAR_U_LOWERCASE:
                        stroke = stroke + "U";
                        break;
                    case Keys_1.Keys.CHAR_V_LOWERCASE:
                        stroke = stroke + "V";
                        break;
                    case Keys_1.Keys.CHAR_W_LOWERCASE:
                        stroke = stroke + "W";
                        break;
                    case Keys_1.Keys.CHAR_X_LOWERCASE:
                        stroke = stroke + "X";
                        break;
                    case Keys_1.Keys.CHAR_Y_LOWERCASE:
                        stroke = stroke + "Y";
                        break;
                    case Keys_1.Keys.CHAR_Z_LOWERCASE:
                        stroke = stroke + "Z";
                        break;
                    default:
                        handled = false;
                        break;
                }
            }
            if (handled) {
                return stroke;
            }
            switch (e.keyCode) {
                case Keys_1.Keys.BACKSPACE:
                    stroke = stroke + "BS";
                    break;
                case Keys_1.Keys.TAB:
                    stroke = stroke + "TAB";
                    break;
                case Keys_1.Keys.CLEAR:
                    stroke = stroke + "CLEAR";
                    break;
                case Keys_1.Keys.ENTER:
                    stroke = stroke + "CR";
                    break;
                case Keys_1.Keys.CAPS_LOCK:
                    stroke = stroke + "CAPS";
                    break;
                case Keys_1.Keys.ESCAPE:
                    stroke = stroke + "ESC";
                    break;
                case Keys_1.Keys.SPACE:
                    stroke = stroke + "SPACE";
                    break;
                case Keys_1.Keys.PAGE_UP:
                    stroke = stroke + "PAGE_UP";
                    break;
                case Keys_1.Keys.PAGE_DOWN:
                    stroke = stroke + "PAGE_DOWN";
                    break;
                case Keys_1.Keys.END:
                    stroke = stroke + "END";
                    break;
                case Keys_1.Keys.HOME:
                    stroke = stroke + "HOME";
                    break;
                case Keys_1.Keys.LEFT_ARROW:
                    stroke = stroke + "ARROW_LEFT";
                    break;
                case Keys_1.Keys.UP_ARROW:
                    stroke = stroke + "ARROW_UP";
                    break;
                case Keys_1.Keys.RIGHT_ARROW:
                    stroke = stroke + "ARROW_RIGHT";
                    break;
                case Keys_1.Keys.DOWN_ARROW:
                    stroke = stroke + "ARROW_DOWN";
                    break;
                case Keys_1.Keys.INSERT:
                    stroke = stroke + "INS";
                    break;
                case Keys_1.Keys.DELETE:
                    stroke = stroke + "DEL";
                    break;
                case Keys_1.Keys.HELP:
                    stroke = stroke + "HELP";
                    break;
                case Keys_1.Keys.LEFT_WINDOW:
                    stroke = stroke + "LEFT_WINDOW";
                    break;
                case Keys_1.Keys.RIGHT_WINDOW:
                    stroke = stroke + "RIGHT_WINDOW";
                    break;
                case Keys_1.Keys.SELECT:
                    stroke = stroke + "SELECT";
                    break;
                case Keys_1.Keys.NUMPAD_0:
                    stroke = stroke + "NUMPAD_0";
                    break;
                case Keys_1.Keys.NUMPAD_1:
                    stroke = stroke + "NUMPAD_1";
                    break;
                case Keys_1.Keys.NUMPAD_2:
                    stroke = stroke + "NUMPAD_2";
                    break;
                case Keys_1.Keys.NUMPAD_3:
                    stroke = stroke + "NUMPAD_3";
                    break;
                case Keys_1.Keys.NUMPAD_4:
                    stroke = stroke + "NUMPAD_4";
                    break;
                case Keys_1.Keys.NUMPAD_5:
                    stroke = stroke + "NUMPAD_5";
                    break;
                case Keys_1.Keys.NUMPAD_6:
                    stroke = stroke + "NUMPAD_6";
                    break;
                case Keys_1.Keys.NUMPAD_7:
                    stroke = stroke + "NUMPAD_7";
                    break;
                case Keys_1.Keys.NUMPAD_8:
                    stroke = stroke + "NUMPAD_8";
                    break;
                case Keys_1.Keys.NUMPAD_9:
                    stroke = stroke + "NUMPAD_9";
                    break;
                case Keys_1.Keys.NUMPAD_MULTIPLY:
                    stroke = stroke + "NUMPAD_MULTIPLY";
                    break;
                case Keys_1.Keys.NUMPAD_PLUS:
                    stroke = stroke + "NUMPAD_PLUS";
                    break;
                case Keys_1.Keys.NUMPAD_ENTER:
                    stroke = stroke + "CR";
                    break;
                case Keys_1.Keys.NUMPAD_MINUS:
                    stroke = stroke + "NUMPAD_MINUS";
                    break;
                case Keys_1.Keys.NUMPAD_PERIOD:
                    stroke = stroke + "NUMPAD_PERIOD";
                    break;
                case Keys_1.Keys.NUMPAD_DIVIDE:
                    stroke = stroke + "NUMPAD_DIVIDE";
                    break;
                case Keys_1.Keys.F1:
                    stroke = stroke + "F1";
                    break;
                case Keys_1.Keys.F2:
                    stroke = stroke + "F2";
                    break;
                case Keys_1.Keys.F3:
                    stroke = stroke + "F3";
                    break;
                case Keys_1.Keys.F4:
                    stroke = stroke + "F4";
                    break;
                case Keys_1.Keys.F5:
                    stroke = stroke + "F5";
                    break;
                case Keys_1.Keys.F6:
                    stroke = stroke + "F6";
                    break;
                case Keys_1.Keys.F7:
                    stroke = stroke + "F7";
                    break;
                case Keys_1.Keys.F8:
                    stroke = stroke + "F8";
                    break;
                case Keys_1.Keys.F9:
                    stroke = stroke + "F9";
                    break;
                case Keys_1.Keys.F10:
                    stroke = stroke + "F10";
                    break;
                case Keys_1.Keys.F11:
                    stroke = stroke + "F11";
                    break;
                case Keys_1.Keys.F12:
                    stroke = stroke + "F12";
                    break;
                case Keys_1.Keys.F13:
                    stroke = stroke + "F13";
                    break;
                case Keys_1.Keys.F14:
                    stroke = stroke + "F14";
                    break;
                case Keys_1.Keys.F15:
                    stroke = stroke + "F15";
                    break;
                case Keys_1.Keys.NUM_LOCK:
                    stroke = stroke + "NUM_LOCK";
                    break;
                case Keys_1.Keys.SCROLL_LOCK:
                    stroke = stroke + "SCROLL_LOCK";
                    break;
                case Keys_1.Keys.UP_DPAD:
                    stroke = stroke + "UP_DPAD";
                    break;
                case Keys_1.Keys.DOWN_DPAD:
                    stroke = stroke + "DOWN_DPAD";
                    break;
                case Keys_1.Keys.LEFT_DPAD:
                    stroke = stroke + "LEFT_DPAD";
                    break;
                case Keys_1.Keys.RIGHT_DPAD:
                    stroke = stroke + "RIGHT_DPAD";
                    break;
                case Keys_1.Keys.NUM_0:
                    stroke = stroke + "0";
                    break;
                case Keys_1.Keys.NUM_1:
                    stroke = stroke + "1";
                    break;
                case Keys_1.Keys.NUM_2:
                    stroke = stroke + "2";
                    break;
                case Keys_1.Keys.NUM_3:
                    stroke = stroke + "3";
                    break;
                case Keys_1.Keys.NUM_4:
                    stroke = stroke + "4";
                    break;
                case Keys_1.Keys.NUM_5:
                    stroke = stroke + "5";
                    break;
                case Keys_1.Keys.NUM_6:
                    stroke = stroke + "6";
                    break;
                case Keys_1.Keys.NUM_7:
                    stroke = stroke + "7";
                    break;
                case Keys_1.Keys.NUM_8:
                    stroke = stroke + "8";
                    break;
                case Keys_1.Keys.NUM_9:
                    stroke = stroke + "9";
                    break;
                case Keys_1.Keys.CHAR_A:
                    stroke = stroke + "A";
                    break;
                case Keys_1.Keys.CHAR_B:
                    stroke = stroke + "B";
                    break;
                case Keys_1.Keys.CHAR_C:
                    stroke = stroke + "C";
                    break;
                case Keys_1.Keys.CHAR_D:
                    stroke = stroke + "D";
                    break;
                case Keys_1.Keys.CHAR_E:
                    stroke = stroke + "E";
                    break;
                case Keys_1.Keys.CHAR_F:
                    stroke = stroke + "F";
                    break;
                case Keys_1.Keys.CHAR_G:
                    stroke = stroke + "G";
                    break;
                case Keys_1.Keys.CHAR_H:
                    stroke = stroke + "H";
                    break;
                case Keys_1.Keys.CHAR_I:
                    stroke = stroke + "I";
                    break;
                case Keys_1.Keys.CHAR_J:
                    stroke = stroke + "J";
                    break;
                case Keys_1.Keys.CHAR_K:
                    stroke = stroke + "K";
                    break;
                case Keys_1.Keys.CHAR_L:
                    stroke = stroke + "L";
                    break;
                case Keys_1.Keys.CHAR_M:
                    stroke = stroke + "M";
                    break;
                case Keys_1.Keys.CHAR_N:
                    stroke = stroke + "N";
                    break;
                case Keys_1.Keys.CHAR_O:
                    stroke = stroke + "O";
                    break;
                case Keys_1.Keys.CHAR_P:
                    stroke = stroke + "P";
                    break;
                case Keys_1.Keys.CHAR_Q:
                    stroke = stroke + "Q";
                    break;
                case Keys_1.Keys.CHAR_R:
                    stroke = stroke + "R";
                    break;
                case Keys_1.Keys.CHAR_S:
                    stroke = stroke + "S";
                    break;
                case Keys_1.Keys.CHAR_T:
                    stroke = stroke + "T";
                    break;
                case Keys_1.Keys.CHAR_U:
                    stroke = stroke + "U";
                    break;
                case Keys_1.Keys.CHAR_V:
                    stroke = stroke + "V";
                    break;
                case Keys_1.Keys.CHAR_W:
                    stroke = stroke + "W";
                    break;
                case Keys_1.Keys.CHAR_X:
                    stroke = stroke + "X";
                    break;
                case Keys_1.Keys.CHAR_Y:
                    stroke = stroke + "Y";
                    break;
                case Keys_1.Keys.CHAR_Z:
                    stroke = stroke + "Z";
                    break;
                case Keys_1.Keys.SLASH:
                    stroke = stroke + "/";
                    break;
                default:
            }
            return stroke;
        };
        return KeystrokeGenerator;
    }());
    exports.KeystrokeGenerator = KeystrokeGenerator;
});
define("ts/event/MessageType", ["require", "exports"], function (require, exports) {
    "use strict";
    (function (MessageType) {
        MessageType[MessageType["INIT"] = 6] = "INIT";
        MessageType[MessageType["WIDGET"] = 22] = "WIDGET";
        MessageType[MessageType["FINISH"] = 38] = "FINISH";
        MessageType[MessageType["ERROR"] = 54] = "ERROR";
        MessageType[MessageType["ACTIVE_ELEMENT"] = 55] = "ACTIVE_ELEMENT";
        MessageType[MessageType["DISPLAY_FILTER"] = 56] = "DISPLAY_FILTER";
        MessageType[MessageType["DISPLAY"] = 58] = "DISPLAY";
    })(exports.MessageType || (exports.MessageType = {}));
    var MessageType = exports.MessageType;
});
define("ts/Config", ["require", "exports"], function (require, exports) {
    "use strict";
    var Config = (function () {
        function Config() {
        }
        Config.init = function (args) {
            if (args.themePath) {
                $("head").find("#adoreTheme").remove();
                $("<link type='text/css' rel='stylesheet'>").attr({
                    "id": "adoreTheme", "href": args.themePath
                }).appendTo(document.head);
            }
            if (args.useMsgServlet && args.httpPort) {
                Config.httpPort = args.httpPort;
                Config.useMsgServlet = args.useMsgServlet;
            }
            if (args.isRowgridLeftInputName) {
                Config._isRowgridLeftInputName = true;
            }
            Config.scale = 1.0;
            if (args.size === "large") {
                Config.changeToLargeScreen();
            }
            else if (args.size === "medium") {
                Config.changeToMediumScreen();
            }
            else if (args.size === "small") {
                Config.changeToSmallScreen();
            }
            else {
                $("meta[name=viewport]").attr("content", "width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no");
            }
            Config.digitOnEditByYuan = args.digitOnEditByYuan;
            Config.isAllowMoneyToEmpty = args.isAllowMoneyToEmpty;
            Config.isEmptyEnterDropDown = args.isEmptyEnterDropDown;
        };
        Config.loadCss = function (args) {
            var $head = $("head");
            for (var index in args.addCssArr) {
                if ($head.find("#" + args.addCssArr[index].id).length === 0) {
                    $("<link type='text/css' rel='stylesheet'>").attr({
                        "id": args.addCssArr[index].id, "href": args.addCssArr[index].path
                    }).appendTo(document.head);
                }
            }
            for (var index in args.removeCssArr) {
                $head.find("#" + args.removeCssArr[index]).remove();
            }
        };
        Config.isRowgridLeftInputName = function () {
            return Config._isRowgridLeftInputName;
        };
        Config.changeToLargeScreen = function () {
            if (window.innerHeight <= 0 || window.innerWidth <= 0) {
                setTimeout(Config.changeToLargeScreen, 300);
                return;
            }
            if (window.innerWidth > window.innerHeight) {
                if (window.innerWidth > Config.lSize) {
                    return;
                }
            }
            else {
                if (window.innerHeight > Config.lSize) {
                    return;
                }
            }
            Config.scale = Config.scale - 0.1;
            Config.fixScale();
            $(document.head).find("meta[name='viewport']").remove();
            $(document.head).append("<meta name='viewport' content='initial-scale=" + Config.scale + ", minimum-scale=" + Config.scale + ", maximum-scale=" + Config.scale + ", user-scalable=no' />");
            if (!Config.isSizeLimit()) {
                setTimeout(Config.changeToLargeScreen, 5);
            }
        };
        Config.changeToMediumScreen = function () {
            if (window.innerHeight <= 0 || window.innerWidth <= 0) {
                setTimeout(Config.changeToMediumScreen, 300);
                return;
            }
            if (window.innerWidth > window.innerHeight) {
                if (window.innerWidth > Config.mSize && window.innerWidth <= Config.lSize) {
                    return;
                }
                if (window.innerWidth > Config.lSize) {
                    Config.scale = Config.scale + 0.1;
                }
                else {
                    Config.scale = Config.scale - 0.1;
                }
            }
            else {
                if (window.innerHeight > Config.mSize && window.innerHeight <= Config.lSize) {
                    return;
                }
                if (window.innerHeight > Config.lSize) {
                    Config.scale = Config.scale + 0.1;
                }
                else {
                    Config.scale = Config.scale - 0.1;
                }
            }
            Config.fixScale();
            $(document.head).find("meta[name='viewport']").remove();
            $(document.head).append("<meta name='viewport' content='initial-scale=" + Config.scale + ", minimum-scale=" + Config.scale + ", maximum-scale=" + Config.scale + ", user-scalable=no' />");
            if (!Config.isSizeLimit()) {
                setTimeout(Config.changeToMediumScreen, 5);
            }
        };
        Config.changeToSmallScreen = function () {
            if (window.innerHeight <= 0 || window.innerWidth <= 0) {
                setTimeout(Config.changeToSmallScreen, 300);
                return;
            }
            if (window.innerWidth > window.innerHeight) {
                if (window.innerWidth <= Config.mSize) {
                    return;
                }
                if (window.innerWidth > Config.mSize) {
                    Config.scale = Config.scale + 0.1;
                }
                else {
                    Config.scale = Config.scale - 0.1;
                }
            }
            else {
                if (window.innerHeight > Config.mSize && window.innerHeight <= Config.lSize) {
                    return;
                }
                if (window.innerHeight > Config.mSize) {
                    Config.scale = Config.scale + 0.1;
                }
                else {
                    Config.scale = Config.scale - 0.1;
                }
            }
            Config.fixScale();
            $(document.head).find("meta[name='viewport']").remove();
            $(document.head).append("<meta name='viewport' content='initial-scale=" + Config.scale + ", minimum-scale=" + Config.scale + ", maximum-scale=" + Config.scale + ", user-scalable=no' />");
            if (!Config.isSizeLimit()) {
                setTimeout(Config.changeToSmallScreen, 5);
            }
        };
        Config.fixScale = function () {
            if (Config.scale < 0.01) {
                Config.scale = 0.01;
            }
            if (Config.scale > 2.0) {
                Config.scale = 2.0;
            }
        };
        Config.isSizeLimit = function () {
            if (Config.scale === 2.0 || Config.scale === 0.01) {
                return true;
            }
            return false;
        };
        Config.httpPort = 8999;
        Config.useMsgServlet = false;
        Config.digitOnEditByYuan = true;
        Config.isAllowMoneyToEmpty = false;
        Config.isEmptyEnterDropDown = false;
        Config._isRowgridLeftInputName = false;
        Config.scale = 1.0;
        Config.lSize = 992;
        Config.mSize = 600;
        return Config;
    }());
    exports.Config = Config;
});
define("ts/event/EventHub", ["require", "exports", "ts/event/MessageType", "ts/Config"], function (require, exports, MessageType_1, Config_1) {
    "use strict";
    var EventHub = (function () {
        function EventHub() {
        }
        EventHub.dispatchEvent = function (msgType, attrs) {
            var evtObj = {
                msgType: msgType,
                attributes: attrs
            };
            var msg = JSON.stringify(evtObj);
            if (window["eventdispatch"]) {
                window["eventdispatch"].dispatch(msg);
                return false;
            }
            else if (window["cefQuery"]) {
                if (msgType === MessageType_1.MessageType.INIT ||
                    msgType === MessageType_1.MessageType.FINISH ||
                    msgType === MessageType_1.MessageType.ERROR ||
                    msgType === MessageType_1.MessageType.DISPLAY) {
                    window["cefQuery"]({
                        request: msg,
                        onSuccess: function (response) {
                        },
                        onFailure: function (error_code, error_message) {
                        }
                    });
                    return false;
                }
                else {
                    try {
                        var regS = new RegExp("\\%", "g");
                        msg = msg.replace(regS, "%25");
                        var url = "adore://message?msg=" + msg + "&timestamp=" + new Date().valueOf();
                        if (Config_1.Config.useMsgServlet) {
                            url = "http://localhost:" + Config_1.Config.httpPort + "/servlets/adore?msg=" + msg;
                        }
                        EventHub.xmlHttpRequest.open("get", url, false);
                        EventHub.xmlHttpRequest.send();
                        return "true" === EventHub.xmlHttpRequest.responseText;
                    }
                    catch (e) {
                        console.error(e);
                        return false;
                    }
                }
            }
            else if (window["dispatch"]) {
                return window["dispatch"](msg);
            }
            else {
                return confirm("adore://" + msg);
            }
        };
        EventHub.xmlHttpRequest = new XMLHttpRequest();
        return EventHub;
    }());
    exports.EventHub = EventHub;
});
define("ts/event/Event", ["require", "exports"], function (require, exports) {
    "use strict";
    (function (Event) {
        Event[Event["DISPOSED"] = 12] = "DISPOSED";
        Event[Event["MOUSE_UP"] = 36] = "MOUSE_UP";
        Event[Event["MOUSE_DOWN"] = 37] = "MOUSE_DOWN";
        Event[Event["CLICK"] = 38] = "CLICK";
        Event[Event["MODIFY"] = 39] = "MODIFY";
        Event[Event["ON_CHANGE"] = 40] = "ON_CHANGE";
        Event[Event["BROWSER_EVENT"] = 41] = "BROWSER_EVENT";
        Event[Event["ON_FOCUS"] = 42] = "ON_FOCUS";
        Event[Event["KEY_UP"] = 43] = "KEY_UP";
        Event[Event["KEY_DOWN"] = 44] = "KEY_DOWN";
        Event[Event["SELECTION"] = 45] = "SELECTION";
        Event[Event["TOUCH_MOVE"] = 46] = "TOUCH_MOVE";
        Event[Event["LOOK_OVER"] = 47] = "LOOK_OVER";
        Event[Event["ON_SLIDING"] = 48] = "ON_SLIDING";
        Event[Event["ON_CLOSE"] = 49] = "ON_CLOSE";
        Event[Event["DOUBLE_CLICK"] = 50] = "DOUBLE_CLICK";
        Event[Event["MOUSE_MOVE"] = 51] = "MOUSE_MOVE";
        Event[Event["FINISH"] = 52] = "FINISH";
        Event[Event["TAB_ADD_CLICK"] = 53] = "TAB_ADD_CLICK";
        Event[Event["FOCUS_OUT"] = 54] = "FOCUS_OUT";
        Event[Event["SLIDE_LEFT"] = 55] = "SLIDE_LEFT";
        Event[Event["SLIDE_RIGHT"] = 56] = "SLIDE_RIGHT";
        Event[Event["ON_CELL_FOCUS"] = 57] = "ON_CELL_FOCUS";
        Event[Event["ON_CELL_BLUR"] = 64] = "ON_CELL_BLUR";
        Event[Event["SLIDE_DOWN"] = 65] = "SLIDE_DOWN";
        Event[Event["SLIDE_UP"] = 66] = "SLIDE_UP";
        Event[Event["CUSTOM_EVENT"] = 67] = "CUSTOM_EVENT";
        Event[Event["SLIDE_CLICK"] = 68] = "SLIDE_CLICK";
        Event[Event["CACHED_ATTRIBUTES_EVENT"] = 69] = "CACHED_ATTRIBUTES_EVENT";
        Event[Event["SEND_CLICK"] = 70] = "SEND_CLICK";
        Event[Event["CONTACT_CLICK"] = 71] = "CONTACT_CLICK";
        Event[Event["RECORD_CLICK"] = 83] = "RECORD_CLICK";
        Event[Event["REFRESH_CLICK"] = 84] = "REFRESH_CLICK";
        Event[Event["PRINT_CLICK"] = 72] = "PRINT_CLICK";
        Event[Event["HEAD_CLICK"] = 73] = "HEAD_CLICK";
        Event[Event["HEAD_DB_CLICK"] = 80] = "HEAD_DB_CLICK";
        Event[Event["WEB_RTC_OPEN"] = 81] = "WEB_RTC_OPEN";
        Event[Event["WEB_RTC_CLOSE"] = 82] = "WEB_RTC_CLOSE";
        Event[Event["DRAG_DROP"] = 96] = "DRAG_DROP";
    })(exports.Event || (exports.Event = {}));
    var Event = exports.Event;
});
define("tradets/AdoreTradeLib", ["require", "exports", "ts/hotkey/KeystrokeGenerator", "ts/event/EventHub", "ts/event/MessageType", "ts/event/Event"], function (require, exports, KeystrokeGenerator_1, EventHub_1, MessageType_2, Event_1) {
    "use strict";
    var AdoreTradeLib = (function () {
        function AdoreTradeLib() {
        }
        AdoreTradeLib.sendHotKey = function (e) {
            var keyStroke = KeystrokeGenerator_1.KeystrokeGenerator.generateKeyStroke(e);
            var keyEventObj = {
                id: "",
                evtType: Event_1.Event.KEY_DOWN,
                altKey: e.altKey,
                shiftKey: e.shiftKey,
                ctrlKey: e.ctrlKey,
                keyCode: e.keyCode,
                keyStroke: keyStroke
            };
            return EventHub_1.EventHub.dispatchEvent(MessageType_2.MessageType.DISPLAY_FILTER, keyEventObj);
        };
        AdoreTradeLib.init = function () {
            $(document).on("keydown", function (e) {
                AdoreTradeLib.sendHotKey(e);
            });
        };
        return AdoreTradeLib;
    }());
    AdoreTradeLib.init();
});
//# sourceMappingURL=AdoreTradeLib.js.map