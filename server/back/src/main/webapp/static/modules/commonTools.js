/**
 * Created by zhangbowen on 2015/6/24.
 */

Array.prototype.indexOf = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
function datagridLoadSuccess() {
    var topIFrame = $('#content_container', top.document);
    var bodyHeight = $(".datagrid-view").outerHeight();
    if ($("div[region='center']").find("th[data-options='width:100,formatter:formatterPhoto']").length > 0) {
        bodyHeight += 50;
    }
    var paginationHeight = $(".datagrid-pager,.pagination").outerHeight();
    //当前右侧iframe里内容高度
    var height = $(".layout-panel-north").outerHeight() + bodyHeight + (paginationHeight > 0 ? paginationHeight : 0) + 10;
    var bodyClient = top.document.body.clientHeight - $(".navbar-static-top", top.document).height() - 1;
    if (bodyClient <= height) {
        topIFrame.height(height + 120);
    } else {
        topIFrame.height(bodyClient);

    }
}
/**
 *定义list顶部工具栏
 * @constructor
 */
var TopSearchBar = function () {
};

TopSearchBar.prototype.initBar = function (settings) {
    this.settings = settings;
    this.initSettings();
    this.bindEvent();
    return this;
}
TopSearchBar.prototype.initSettings = function () {
    this.initMembers = function (settingName, defaultValue) {
        this.settings[settingName] = (this.settings[settingName] == undefined) ? defaultValue : this.settings[settingName];
    };
    this.initMembers("gridObj", $("#listDataGrid"));
    this.initMembers("queryColumns", []);
    this.initMembers("queryListBt", $("#queryListBt"));
    this.initMembers("clearBarBt", $("#clearBarBt"));
}
TopSearchBar.prototype.getQueryDatas = function (isEncode) {
    isEncode = typeof isEncode == 'undefined' ? false : isEncode;
    var queryParams = {};
    $.each(this.settings["queryColumns"], function (i, e) {
        var jqObj = $("#" + e);
        var jqValue = "";
        if (jqObj.hasClass("easyui-textbox")) {
            jqValue = jqObj.textbox('getValue');
        } else if (jqObj.hasClass("easyui-combobox")) {
            jqValue = jqObj.combobox('getValue')
        } else if (jqObj.hasClass("easyui-combbox")) {
            jqValue = jqObj.combobox('getText')
        } else if (jqObj.hasClass("easyui-datetimebox")) {
            jqValue = jqObj.datetimebox('getValue')
        } else if (jqObj.hasClass("easyui-datebox")) {
            jqValue = jqObj.datebox('getValue')
        }else {
            jqValue = jqObj.val();
        }
        if (jqValue) {
            queryParams[e] = isEncode ? encodeURIComponent(jqValue) : jqValue;
        }
    });
    console.log(queryParams);
    return queryParams;
}
TopSearchBar.prototype.bindEvent = function () {
    var searchBar = this;
    var settings = this.settings;
    /**
     * 查询
     */
    settings["queryListBt"].on("click", function () {
        var queryParams = searchBar.getQueryDatas();
        var jqGrid = settings["gridObj"];
        if (jqGrid.hasClass("easyui-datagrid")) {
            jqGrid.datagrid('load', queryParams);
        } else if (jqGrid.hasClass("easyui-treegrid")) {
            jqGrid.treegrid('load', queryParams);
        }

    });
    /**
     * 清空
     */
    settings["clearBarBt"].on("click", function () {
        $.each(settings["queryColumns"], function (i, e) {
            $("#" + e + '.easyui-textbox').textbox('clear');
            $("#" + e + '.easyui-combobox').combobox('setValue', '');
            $("#" + e + '.easyui-datetimebox').datetimebox('setValue', '');
            $("#" + e + '.easyui-datebox').datebox('setValue', '');
        });
    })
}

/**
 * 格式化列表图片
 * @param value
 * @returns {string}
 */
function formatterPhoto(value) {
    return '<img alt="图片" style="width:75px;height:50px;margin:0px;" src="' + value + '">';
}
/**
 * 打开全屏窗口
 * @param $window
 * @param title
 * @param windowId
 * @param content
 */
function openFullWindow(title, windowId) {
    openFullWindow(title, windowId, null);
}
function openWindow(title, windowId, param, _width, _height) {
    var $window = $('#' + windowId);
    var url = $window.attr("url");
    if (param) {
        if (param) {
            url += ((url.indexOf('?') != -1 ? '&' : '?') + param);
        }
    }
    var windowSettings = {
        width: _width,
        height: _height,
        modal: true,
        cache: false,
        title: title,
        content: '<iframe name="' + title + '"src="' + url + '" width="100%" height="100%" frameborder="0" scrolling="auto" ></iframe>',
        top: (top.document.body.clientHeight - height) / 2
    };
    $window.window(windowSettings).window("center").window("open");
}
function openCenterWindow(title, windowId, param) {
    openWindow(title, windowId, param, $(window).width() / 3 * 2, $(window).height() / 3 * 2);
}
function openFullWindow(title, windowId, param) {
    var $window = $('#' + windowId);
    var url = $window.attr("url");
    if (param) {
        url += ((url.indexOf('?') != -1 ? '&' : '?') + param);
    }
    var windowSettings = {
        width: $(window).width() / 3 * 2,
        height: $(window).height() / 3 * 2,
        modal: true,
        cache: false,
        title: title,
        content: '<iframe name="' + title + '"src="' + url + '" width="100%" height="100%" frameborder="0" scrolling="auto" ></iframe>'
    };
    var width = $window.attr("width");
    var height = $window.attr("height");
    if (width) {
        windowSettings["width"] = parseInt(width);
    }
    if (height) {
        windowSettings["height"] = parseInt(height);
    }
    if (!width && !height) {
        windowSettings["maximized"] = true;
        $window.window(windowSettings).window("open");
    } else {
        windowSettings["top"] = (top.document.body.clientHeight - height) / 2;
        $window.window(windowSettings).window("open");
    }
}
function getLayerCenter() {
    return (top.document.body.clientHeight - 100) / 2;
}
/**
 * 操作成功
 */
function showSuccess() {
    layer.msg("操作成功", {
        offset: getLayerCenter()
    });
}

/**
 Jquery easyui datagrid js导出excel
 修改自extgrid导出excel
 * allows for downloading of grid data (store) directly into excel
 * Method: extracts data of gridPanel store, uses columnModel to construct XML excel document,
 * converts to Base64, then loads everything into a data URL link.
 */
$.extend($.fn.datagrid.methods, {
    getExcelXml: function (jq, param) {
        var worksheet = this.createWorksheet(jq, param);
        var totalWidth = 0;
        n
        var cfs = $(jq).datagrid('getColumnFields');
        for (var i = 0; i < cfs.length; i++) {
            totalWidth += $(jq).datagrid('getColumnOption', cfs[i]).width;
        }
        return '<?xml version="1.0" encoding="utf-8"?>' +
            '<ss:Workbook xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns:o="urn:schemas-microsoft-com:office:office">' +
            '<o:DocumentProperties><o:Title>' + param.title + '</o:Title></o:DocumentProperties>' +
            '<ss:ExcelWorkbook>' +
            '<ss:WindowHeight>' + worksheet.height + '</ss:WindowHeight>' +
            '<ss:WindowWidth>' + worksheet.width + '</ss:WindowWidth>' +
            '<ss:ProtectStructure>False</ss:ProtectStructure>' +
            '<ss:ProtectWindows>False</ss:ProtectWindows>' +
            '</ss:ExcelWorkbook>' +
            '<ss:Styles>' +
            '<ss:Style ss:ID="Default">' +
            '<ss:Alignment ss:Vertical="Top"  />' +
            '<ss:Font ss:FontName="arial" ss:Size="10" />' +
            '<ss:Borders>' +
            '<ss:Border  ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Top" />' +
            '<ss:Border  ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Bottom" />' +
            '<ss:Border  ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Left" />' +
            '<ss:Border ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Right" />' +
            '</ss:Borders>' +
            '<ss:Interior />' +
            '<ss:NumberFormat />' +
            '<ss:Protection />' +
            '</ss:Style>' +
            '<ss:Style ss:ID="title">' +
            '<ss:Borders />' +
            '<ss:Font />' +
            '<ss:Alignment  ss:Vertical="Center" ss:Horizontal="Center" />' +
            '<ss:NumberFormat ss:Format="@" />' +
            '</ss:Style>' +
            '<ss:Style ss:ID="headercell">' +
            '<ss:Font ss:Bold="1" ss:Size="10" />' +
            '<ss:Alignment  ss:Horizontal="Center" />' +
            '<ss:Interior ss:Pattern="Solid"  />' +
            '</ss:Style>' +
            '<ss:Style ss:ID="even">' +
            '<ss:Interior ss:Pattern="Solid"  />' +
            '</ss:Style>' +
            '<ss:Style ss:Parent="even" ss:ID="evendate">' +
            '<ss:NumberFormat ss:Format="yyyy-mm-dd" />' +
            '</ss:Style>' +
            '<ss:Style ss:Parent="even" ss:ID="evenint">' +
            '<ss:NumberFormat ss:Format="0" />' +
            '</ss:Style>' +
            '<ss:Style ss:Parent="even" ss:ID="evenfloat">' +
            '<ss:NumberFormat ss:Format="0.00" />' +
            '</ss:Style>' +
            '<ss:Style ss:ID="odd">' +
            '<ss:Interior ss:Pattern="Solid"  />' +
            '</ss:Style>' +
            '<ss:Style ss:Parent="odd" ss:ID="odddate">' +
            '<ss:NumberFormat ss:Format="yyyy-mm-dd" />' +
            '</ss:Style>' +
            '<ss:Style ss:Parent="odd" ss:ID="oddint">' +
            '<ss:NumberFormat ss:Format="0" />' +
            '</ss:Style>' +
            '<ss:Style ss:Parent="odd" ss:ID="oddfloat">' +
            '<ss:NumberFormat ss:Format="0.00" />' +
            '</ss:Style>' +
            '</ss:Styles>' +
            worksheet.xml +
            '</ss:Workbook>';
    },
    createWorksheet: function (jq, param) {
        // Calculate cell data types and extra class names which affect formatting
        var cellType = [];
        var cellTypeClass = [];
        //var cm = this.getColumnModel();
        var totalWidthInPixels = 0;
        var colXml = '';
        var headerXml = '';
        var visibleColumnCountReduction = 0;
        var cfs = $(jq).datagrid('getColumnFields');
        var colCount = cfs.length;
        for (var i = 0; i < colCount; i++) {
            if (cfs[i] != '') {
                var w = $(jq).datagrid('getColumnOption', cfs[i]).width;
                totalWidthInPixels += w;
                if (cfs[i] === "") {
                    cellType.push("None");
                    cellTypeClass.push("");
                    ++visibleColumnCountReduction;
                }
                else {
                    colXml += '<ss:Column ss:AutoFitWidth="1" ss:Width="130" />';
                    headerXml += '<ss:Cell ss:StyleID="headercell">' +
                        '<ss:Data ss:Type="String">' + $(jq).datagrid('getColumnOption', cfs[i]).title + '</ss:Data>' +
                        '<ss:NamedCell ss:Name="Print_Titles" /></ss:Cell>';
                    cellType.push("String");
                    cellTypeClass.push("");
                }
            }
        }
        var visibleColumnCount = cellType.length - visibleColumnCountReduction;
        var result = {
            height: 9000,
            width: Math.floor(totalWidthInPixels * 30) + 50
        };
        var rows = $(jq).datagrid('getRows');
        // Generate worksheet header details.
        var t = '<ss:Worksheet ss:Name="' + param.title + '">' +
            '<ss:Names>' +
            '<ss:NamedRange ss:Name="Print_Titles" ss:RefersTo="=\'' + param.title + '\'!R1:R2" />' +
            '</ss:Names>' +
            '<ss:Table x:FullRows="1" x:FullColumns="1"' +
            ' ss:ExpandedColumnCount="' + (visibleColumnCount + 2) +
            '" ss:ExpandedRowCount="' + (rows.length + 2) + '">' +
            colXml +
            '<ss:Row ss:AutoFitHeight="1">' +
            headerXml +
            '</ss:Row>';
        // Generate the data rows from the data in the Store
        for (var i = 0, it = rows, l = it.length; i < l; i++) {
            t += '<ss:Row>';
            var cellClass = (i & 1) ? 'odd' : 'even';
            r = it[i];
            var k = 0;
            for (var j = 0; j < colCount; j++) {
                var columnName = cfs[j];
                if (columnName != '') {
                    var v = r[columnName];
                    if (v == undefined) {
                        v = "";
                    }
                    //新增fomatter
                    var columnObj = jq.datagrid('getColumnOption', columnName);
                    if (columnObj.formatter != undefined) {
                        v = columnObj.formatter(v);
                    }
                    if (cellType[k] !== "None") {
                        t += '<ss:Cell ss:StyleID="' + cellClass + cellTypeClass[k] + '"><ss:Data ss:Type="' + cellType[k] + '">';
                        if (cellType[k] == 'DateTime') {
                            t += v.format('Y-m-d');
                        } else {
                            t += v;
                        }
                        t += '</ss:Data></ss:Cell>';
                    }
                    k++;
                }
            }
            t += '</ss:Row>';
        }
        result.xml = t + '</ss:Table>' +
            '<x:WorksheetOptions>' +
            '<x:PageSetup>' +
            '<x:Layout x:CenterHorizontal="1" x:Orientation="Landscape" />' +
            '<x:Footer x:Data="Page &P of &N" x:Margin="0.5" />' +
            '<x:PageMargins x:Top="0.5" x:Right="0.5" x:Left="0.5" x:Bottom="0.8" />' +
            '</x:PageSetup>' +
            '<x:FitToPage />' +
            '<x:Print>' +
            '<x:PrintErrors>Blank</x:PrintErrors>' +
            '<x:FitWidth>1</x:FitWidth>' +
            '<x:FitHeight>32767</x:FitHeight>' +
            '<x:ValidPrinterInfo />' +
            '<x:VerticalResolution>600</x:VerticalResolution>' +
            '</x:Print>' +
            '<x:Selected />' +
            '<x:DoNotDisplayGridlines />' +
            '<x:ProtectObjects>False</x:ProtectObjects>' +
            '<x:ProtectScenarios>False</x:ProtectScenarios>' +
            '</x:WorksheetOptions>' +
            '</ss:Worksheet>';
        return result;
    }
});


/**
 * 初始化
 * @constructor
 */
var BaseOperationBt = function (settings) {
    this.init(settings);
    return this;
};

BaseOperationBt.prototype.init = function (settings) {
    this.settings = settings;
    this.initSettings();
    this.bindEvent();
}
BaseOperationBt.prototype.initSettings = function () {
    this.initMembers = function (settingName, defaultValue) {
        this.settings[settingName] = (this.settings[settingName] == undefined) ? defaultValue : this.settings[settingName];
    };
    this.initMembers("listDataGrid", $('#listDataGrid'));
    this.initMembers("addInfoBt", $("#addInfoBt"));
    this.initMembers("addWindowEvent", 'openFullWindow("添加","addWindow")');
    this.initMembers("editInfoBt", $("#editInfoBt"));
    this.initMembers("deleteBt", $("#deleteBt"));
    this.initMembers("deleteUrl", "");
    this.initMembers("editWindow", "editWindow");

    this.initMembers(".editWindow", $('.editWindow'));
}
/**
 * 设置回调事件
 * @param callBackEventName
 * @param callBackEvent
 */
BaseOperationBt.prototype.setCallBackEvent = function (callBackEventName, callBackEvent) {
    this.settings[callBackEventName] = callBackEvent;
}
BaseOperationBt.prototype.baseUpdate = function (the) {
    var settings = this.settings;
    var row = settings["listDataGrid"].datagrid('getSelected');
    if (row) {
        var param = "id=" + row.id;
        if (settings.updateParam && settings.updateParam.length > 0) {
            $.each(settings.updateParam, function (i, obj) {
                param = param + "&" + obj + "=" + row[obj];
            });
        }

        var msg = (settings.before || function(){})(row, the);
        if(typeof msg != 'undefined'){
            layer.msg(msg, {
                offset: getLayerCenter()
            });
        }else{
            if(!the){
                openFullWindow("编辑", settings["editWindow"], param);
            }else{
                $('#editWindowCommon').attr('url', the.data('url'));
                openFullWindow(the.data('title'), 'editWindowCommon', param);
            }
        }
    } else {
        layer.msg('请选择', {
            offset: getLayerCenter()
        });
    }
}
/**
 * 基本删除
 * @param url
 */
BaseOperationBt.prototype.baseDelete = function (url) {
    var settings = this.settings;
    var row = settings["listDataGrid"].datagrid('getSelected');
    if (row) {
        var str = '该操作会删除与之关联的所有数据（不可恢复）？'
        $.messager.confirm('确认', str, function (r) {
            if (r) {
                $.post(url, {id: row.id}, function (result) {
                    if (result.code == 0) {
                        showSuccess();
                        if (settings["listDataGrid"].hasClass("easyui-datagrid")) {
                            settings["listDataGrid"].datagrid('reload');
                        } else if (settings["listDataGrid"].hasClass("easyui-treegrid")) {
                            settings["listDataGrid"].treegrid('reload');
                        }
                    } else {
                        layer.msg(result.msg, {
                            offset: getLayerCenter()
                        });
                    }
                }, 'json');
            }
        });
    } else {
        layer.msg('请选择', {
            offset: getLayerCenter()
        });
    }
}
BaseOperationBt.prototype.bindEvent = function () {
    var settings = this.settings;
    var self = this;
    /**
     * 新增window
     */
    settings["addInfoBt"].on("click", function () {
        eval('(' + settings["addWindowEvent"] + ')');
    });
    /**
     * 编辑window
     */
    settings["editInfoBt"].on("click", function () {
        self.baseUpdate(settings["updateWindowUrl"]);
    })
    /**
     * 删除
     */
    settings["deleteBt"].on("click", function () {
        self.baseDelete(settings["deleteUrl"]);
    })

    // 普通编辑窗口
    settings[".editWindow"].on("click", function () {
        self.baseUpdate($(this));
    })

}

/**
 * 上传图片组件扩展
 * @param settings
 * @constructor
 */
var FileInputCustom = function (config, inputSettings) {
    this.init(config, inputSettings);
};
FileInputCustom.prototype.defaults = {
    language: "zh",
    uploadAsync: true,
    allowedFileExtensions: ['jpg', 'png', 'zip'],
    uploadUrl: ctx + "/upload",
    autoReplace: true,
    overwriteInitial: true,
    showUploadedThumbs: false,
    validateInitialCount: true,
    maxFileCount: 1,
    useDefaultName: false

}
FileInputCustom.prototype.init = function (config, inputSettings) {
    if (!inputSettings) {
        inputSettings = {};
    }
    this.settings = config;
    this.settings.uploadedUrls = [];
    var $inputFile = config.$inputFile;//fileInput控件
    if (inputSettings.dataUrls) {
        var initImages = inputSettings.dataUrls.split(",");
        var initialPreview = [];
        var initialPreviewConfig = [];
        if (inputSettings.dataUrls.length > 0) {
            $.each(initImages, function (i, obj) {
                this.settings.uploadedUrls.push(obj);
                initialPreview.push("<img style='height:160px' src='"+ctx +"/" + obj + "'>");
                var config = {
                    width: "120px",
                    url: ctx + "/upload/delete",
                    key: i,
                    extra: obj
                }
                initialPreviewConfig.push(config);
            }.bind(this))
        }
        inputSettings.initialPreview = initialPreview;
        inputSettings.initialPreviewConfig = initialPreviewConfig;
    }
    this.inputFileSettings = $.extend({}, this.defaults, inputSettings);
    if (this.inputFileSettings.maxFileCount > 1) {
        this.inputFileSettings.autoReplace = false;
        this.inputFileSettings.overwriteInitial = false;
    }
    if (this.inputFileSettings.useDefaultName) {
        this.inputFileSettings.uploadUrl = this.inputFileSettings.uploadUrl + "?useDefaultName=true"
    }
    //初始化上传图片控件
    $inputFile.fileinput(this.inputFileSettings);
    $inputFile.on('fileuploaded', function (event, data, id, index) {
        this.settings.uploadedUrls.push(data.response.url);
        $("#" + id).attr("dataUrl", data.response.url);
    }.bind(this));

    $inputFile.on('filecleared', function (event) {
        this.settings.uploadedUrls.length = 0;
    }.bind(this));
    $inputFile.on('filepreupload', function (event, data, previewId, index) {
        if (this.inputFileSettings.maxFileCount == 1) {
            this.settings.uploadedUrls.length = 0;
        } else {
            if (this.inputFileSettings.maxFileCount <= this.settings.uploadedUrls.length) {
                this.settings.uploadedUrls.remove(this.settings.uploadedUrls.length - 1);
            }
        }
    }.bind(this));
    $inputFile.on('filedeleted', function (event, key, jqXHR, data) {
        this.settings.uploadedUrls.remove(data);
    }.bind(this));
    $inputFile.on('filesuccessremove', function (event, id) {
        this.settings.uploadedUrls.remove($("#" + id).attr("dataUrl"));
    }.bind(this));
}
/**
 * 设置上传的url地址到input中
 */
FileInputCustom.prototype.setUploadUrls = function () {
    var urls = "";
    $.each(this.settings.uploadedUrls, function (index, obj) {
        if (index > 0) {
            urls += ',' + obj;
        } else {
            urls += obj;
        }
    });
    this.settings.$resUrl.val(urls);

    return urls;
}
FileInputCustom.prototype.checkUrls = function () {
    var urls = this.setUploadUrls();
    return urls && urls.length > 0;
}


function cancelForm() {
    parent.window.$("iframe").parent().window("close");
}
/**
 * 表单页面的提交操作
 */
function infoAjaxSubmit(source, url, callback) {
    var options = {
        url: url,
        dataType: 'json',
        type: "POST",
        beforeSend: function () {
            ajaxLayerLoadingIndex = layer.load(1, {
                offset: getLayerCenter(),
                shade: [0.2, '#000000']
            });
        },
        complete: function () {
            layer.close(ajaxLayerLoadingIndex);
        },
        success: function (result) {
            if (callback) {
                callback(result);
            } else {
                if (result.code == 0) {
                    var _win = parent.window || window;
                    // TODO 关闭子窗口，父窗口弹出遮罩提示，清空当前页面文本框
                    $('input').val('');
                    try {
                        _win.$("iframe").parent().window("close");
                    }catch (e){

                    }
                    var $grid = parent.$("#listDataGrid");
                    if ($grid.hasClass("easyui-datagrid")) {
                        $grid.datagrid('reload');
                    } else if ($grid.hasClass("easyui-treegrid")) {
                        $grid.treegrid('reload');
                    }
                    _win.layer.msg("操作成功", {
                        offset: getLayerCenter()
                    });
                } else {
                    layer.msg(result.msg, {
                        offset: getLayerCenter()
                    });
                }
            }
        }
    };
    $(source).parent().ajaxForm(options);
}

var _isIng = false;
/**
 * POST  参数传递方式 requestBody流 json字符串
 * @param source
 * @param url
 * @param data
 * @param callback
 */
function infoAjax(source, url, data, callback){
    if(_isIng){
        return;
    }
    _isIng = true;
    ajaxLayerLoadingIndex = layer.load(1, {
        offset: getLayerCenter(),
        shade: [0.2, '#000000']
    });
    $.ajax({
        url: url,
        dataType: 'json',
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function(result){
            _isIng = false;
            if (callback) {
                callback(result);
            } else {
                if (result.code == 0) {
                    var _win = parent.window || window;
                    // TODO 关闭子窗口，父窗口弹出遮罩提示
                    try {
                        _win.$("iframe").parent().window("close");
                    }catch (e){

                    }
                    var $grid = parent.$("#listDataGrid");
                    if ($grid.hasClass("easyui-datagrid")) {
                        $grid.datagrid('reload');
                    } else if ($grid.hasClass("easyui-treegrid")) {
                        $grid.treegrid('reload');
                    }
                    _win.layer.msg("操作成功", {
                        offset: getLayerCenter()
                    });
                } else {
                    layer.msg(result.msg, {
                        offset: getLayerCenter()
                    });
                }
            }
            layer.close(ajaxLayerLoadingIndex);

        },
        error: function(){
            _isIng = false;
            layer.close(ajaxLayerLoadingIndex);
        }
    });
}

/**
 * 模态框的提交操作
 */
function infoAjaxSubmitModal($form, url, successCallBack) {
    var options = {
        url: url,
        dataType: 'json',
        type: "POST",
        success: function (result) {
            successCallBack(result);
        }
    };
    $form.ajaxForm(options);
}

var CkEditorCustom = function (settings) {
    this.settings = settings;
    this.init();
    this.initEditor();
};
CkEditorCustom.prototype.init = function () {
    this.initMembers = function (settingName, defaultValue) {
        this.settings[settingName] = (this.settings[settingName] == undefined) ? defaultValue : this.settings[settingName];
    };
    this.initMembers("height", 150);
    this.initMembers("editorId", "ckEditor");

    if (CKEDITOR.env.ie && CKEDITOR.env.version < 9)
        CKEDITOR.tools.enableHtml5Elements(document);
    CKEDITOR.config.height = this.settings["height"];
    CKEDITOR.config.width = 'auto';
}
CkEditorCustom.prototype.initEditor = function () {
    var self = this;

    function isWysiwygareaAvailable() {
        if (CKEDITOR.revision == ( '%RE' + 'V%' )) {
            return true;
        }
        return !!CKEDITOR.plugins.get('wysiwygarea');
    }

    var wysiwygareaAvailable = isWysiwygareaAvailable();
    return function () {
        var editorElement = CKEDITOR.document.getById(self.settings["editorId"]);
        if (wysiwygareaAvailable) {
            CKEDITOR.replace(self.settings["editorId"]);
        } else {
            editorElement.setAttribute('contenteditable', 'true');
            CKEDITOR.inline(self.settings["editorId"]);
        }
    }();
}


CkEditorCustom.prototype.setInputDatas = function () {
    var content = CKEDITOR.instances[this.settings["editorId"]].getData();
    this.settings.$targetInput.val(content);
    return content && content.length > 0;
}

/**
 * 获取script标签中的参数
 * @param key
 * @returns {*}
 */
var getScriptArg = function (key) {//获取单个参数
    var scripts = document.getElementsByTagName("script"),
        script = scripts[scripts.length - 1],
        src = script.src;
    return (src.match(new RegExp("(?:\\?|&)" + key + "=(.*?)(?=&|$)")) || ['', null])[1];
};
/**
 * 上传图片提交前获取图片宽高大小
 */
var imgReady = (function () {
    var list = [], intervalId = null,

        // 用来执行队列
        tick = function () {
            var i = 0;
            for (; i < list.length; i++) {
                list[i].end ? list.splice(i--, 1) : list[i]();
            }
            ;
            !list.length && stop();
        },

        // 停止所有定时器队列
        stop = function () {
            clearInterval(intervalId);
            intervalId = null;
        };

    return function (url, ready, load, error) {
        var onready, width, height, newWidth, newHeight,
            img = new Image();

        img.src = url;

        // 如果图片被缓存，则直接返回缓存数据
        if (img.complete) {
            ready.call(img);
            load && load.call(img);
            return;
        }
        ;

        width = img.width;
        height = img.height;

        // 加载错误后的事件
        img.onerror = function () {
            error && error.call(img);
            onready.end = true;
            img = img.onload = img.onerror = null;
        };

        // 图片尺寸就绪
        onready = function () {
            newWidth = img.width;
            newHeight = img.height;
            if (newWidth !== width || newHeight !== height ||
                // 如果图片已经在其他地方加载可使用面积检测
                newWidth * newHeight > 1024
            ) {
                ready.call(img);
                onready.end = true;
            }
            ;
        };
        onready();

        // 完全加载完毕的事件
        img.onload = function () {
            // onload在定时器时间差范围内可能比onready快
            // 这里进行检查并保证onready优先执行
            !onready.end && onready();

            load && load.call(img);

            // IE gif动画会循环执行onload，置空onload即可
            img = img.onload = img.onerror = null;
        };

        // 加入队列中定期执行
        if (!onready.end) {
            list.push(onready);
            // 无论何时只允许出现一个定时器，减少浏览器性能损耗
            if (intervalId === null) intervalId = setInterval(tick, 40);
        }
        ;
    };
})();
/**
 * 图片尺寸限制
 * @param rightWidth 规定尺寸
 * @param rightHeight
 * @param nowWidth 当前图片尺寸
 * @param nowHeight
 */
function checkImgPX(rightWidth, rightHeight, nowWidth, nowHeight) {
    if (rightWidth != nowWidth || rightHeight != nowHeight) {
        $.messager.alert("提示", "图片尺寸必须为" + rightWidth + "px*" + rightHeight + "px");
        return false;
    } else {
        return true;
    }
}
