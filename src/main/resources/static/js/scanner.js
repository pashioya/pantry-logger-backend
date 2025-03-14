$(function() {
    let CodesMap = {
        map : new Map(),
        getMax : function() {
            const interator = this.map.keys();
            let maxAmount = 0;
            let maxCode = 0;
            for (const currentCode of interator) {
                maxCode = maxAmount > this.map.get(currentCode) ? maxCode : currentCode;
                maxAmount = maxAmount > this.map.get(currentCode) ? maxAmount : this.map.get(currentCode);
            }
            return maxCode;
        },
        addCode : function(code) {
            if (this.map.has(code)) {
                this.map.set(code, this.map.get(code) + 1);
            } else {
                this.map.set(code, 1);
            }
        },
        clear : function () {
            this.map = new Map();
        }
    };
    let App = {
        init : function() {
            Quagga.init(this.state, function(err) {
                if (err) {
                    console.log(err);
                    return;
                }
                //App.attachListeners();
                App.checkCapabilities();
                Quagga.start();
            });
        },
        checkCapabilities: function() {
            var track = Quagga.CameraAccess.getActiveTrack();
            var capabilities = {};
            if (typeof track.getCapabilities === 'function') {
                capabilities = track.getCapabilities();
            }
            this.applySettingsVisibility('zoom', capabilities.zoom);
            this.applySettingsVisibility('torch', capabilities.torch);
        },
        updateOptionsForMediaRange: function(node, range) {
            console.log('updateOptionsForMediaRange', node, range);
            var NUM_STEPS = 6;
            var stepSize = (range.max - range.min) / NUM_STEPS;
            var option;
            var value;
            while (node.firstChild) {
                node.removeChild(node.firstChild);
            }
            for (var i = 0; i <= NUM_STEPS; i++) {
                value = range.min + (stepSize * i);
                option = document.createElement('option');
                option.value = value;
                option.innerHTML = value;
                node.appendChild(option);
            }
        },
        applySettingsVisibility: function(setting, capability) {
            // depending on type of capability
            if (typeof capability === 'boolean') {
                var node = document.querySelector('input[name="settings_' + setting + '"]');
                if (node) {
                    node.parentNode.style.display = capability ? 'block' : 'none';
                }
                return;
            }
            if (window.MediaSettingsRange && capability instanceof window.MediaSettingsRange) {
                var node = document.querySelector('select[name="settings_' + setting + '"]');
                if (node) {
                    this.updateOptionsForMediaRange(node, capability);
                    node.parentNode.style.display = 'block';
                }
                return;
            }
        },
        initCameraSelection: function(){
            var streamLabel = Quagga.CameraAccess.getActiveStreamLabel();

            return Quagga.CameraAccess.enumerateVideoDevices()
            .then(function(devices) {
                function pruneText(text) {
                    return text.length > 30 ? text.substr(0, 30) : text;
                }
                var $deviceSelection = document.getElementById("deviceSelection");
                while ($deviceSelection.firstChild) {
                    $deviceSelection.removeChild($deviceSelection.firstChild);
                }
                devices.forEach(function(device) {
                    var $option = document.createElement("option");
                    $option.value = device.deviceId || device.id;
                    $option.appendChild(document.createTextNode(pruneText(device.label || device.deviceId || device.id)));
                    $option.selected = streamLabel === device.label;
                    $deviceSelection.appendChild($option);
                });
            });
        },
        /*attachListeners: function() {
            var self = this;

            self.initCameraSelection();
            $(".controls").on("click", "button.stop", function(e) {
                e.preventDefault();
                Quagga.stop();
            });

            $(".controls .reader-config-group").on("change", "input, select", function(e) {
                e.preventDefault();
                var $target = $(e.target),
                    value = $target.attr("type") === "checkbox" ? $target.prop("checked") : $target.val(),
                    name = $target.attr("name"),
                    state = self._convertNameToState(name);

                console.log("Value of "+ state + " changed to " + value);
                self.setState(state, value);
            });
        },*/
        _accessByPath: function(obj, path, val) {
            var parts = path.split('.'),
                depth = parts.length,
                setter = (typeof val !== "undefined") ? true : false;

            return parts.reduce(function(o, key, i) {
                if (setter && (i + 1) === depth) {
                    if (typeof o[key] === "object" && typeof val === "object") {
                        Object.assign(o[key], val);
                    } else {
                        o[key] = val;
                    }
                }
                return key in o ? o[key] : {};
            }, obj);
        },
        _convertNameToState: function(name) {
            return name.replace("_", ".").split("-").reduce(function(result, value) {
                return result + value.charAt(0).toUpperCase() + value.substring(1);
            });
        },
        /*detachListeners: function() {
            $(".controls").off("click", "button.stop");
            $(".controls .reader-config-group").off("change", "input, select");
        },*/
        applySetting: function(setting, value) {
            var track = Quagga.CameraAccess.getActiveTrack();
            if (track && typeof track.getCapabilities === 'function') {
                switch (setting) {
                case 'zoom':
                    return track.applyConstraints({advanced: [{zoom: parseFloat(value)}]});
                case 'torch':
                    return track.applyConstraints({advanced: [{torch: !!value}]});
                }
            }
        },
        setState: function(path, value) {
            var self = this;

            if (typeof self._accessByPath(self.inputMapper, path) === "function") {
                value = self._accessByPath(self.inputMapper, path)(value);
            }

            if (path.startsWith('settings.')) {
                var setting = path.substring(9);
                return self.applySetting(setting, value);
            }
            self._accessByPath(self.state, path, value);

            console.log(JSON.stringify(self.state));
            App.detachListeners();
            Quagga.stop();
            App.init();
        },
        inputMapper: {
            inputStream: {
                constraints: function(value){
                    if (/^(\d+)x(\d+)$/.test(value)) {
                        var values = value.split('x');
                        return {
                            width: {min: parseInt(values[0])},
                            height: {min: parseInt(values[1])}
                        };
                    }
                    return {
                        deviceId: value
                    };
                }
            },
            numOfWorkers: function(value) {
                return parseInt(value);
            },
            decoder: {
                readers: function(value) {
                    if (value === 'ean_extended') {
                        return [{
                            format: "ean_reader",
                            config: {
                                supplements: [
                                    'ean_5_reader', 'ean_2_reader'
                                ]
                            }
                        }];
                    }
                    return [{
                        format: value + "_reader",
                        config: {}
                    }];
                }
            }
        },
        state: {
            inputStream: {
                type : "LiveStream",
                constraints: {
                    width: {min: 640},
                    height: {min: 480},
                    aspectRatio: {min: 1, max: 100},
                    facingMode: "environment" // or user
                }
            },
            locator: {
                patchSize: "medium",
                halfSample: true
            },
            numOfWorkers: 2,
            frequency: 5,
            decoder: {
                readers : [{
                    format: "ean_reader",
                    config: {}
                }]
            },
            locate: true
        },
        currentNumberScans : 0
    };

    App.init();

    Quagga.onProcessed(function(result) {
        const drawingCtx = Quagga.canvas.ctx.overlay,
            drawingCanvas = Quagga.canvas.dom.overlay;

        if (result) {
            if (result.boxes) {
                drawingCtx.clearRect(0, 0, parseInt(drawingCanvas.getAttribute("width")), parseInt(drawingCanvas.getAttribute("height")));
                result.boxes.filter(function (box) {
                    return box !== result.box;
                }).forEach(function (box) {
                    Quagga.ImageDebug.drawPath(box, {x: 0, y: 1}, drawingCtx, {color: "green", lineWidth: 2});
                });
            }

            if (result.box) {
                Quagga.ImageDebug.drawPath(result.box, {x: 0, y: 1}, drawingCtx, {color: "#00F", lineWidth: 2});
            }

            if (result.codeResult && result.codeResult.code) {
                Quagga.ImageDebug.drawPath(result.line, {x: 'x', y: 'y'}, drawingCtx, {color: 'red', lineWidth: 3});
            }
        }
    });

    Quagga.onDetected(function(result) {
        const minNumberOfScans = 5;
        CodesMap.addCode(result.codeResult.code);

        if (App.currentNumberScans > minNumberOfScans) {
            let code = CodesMap.getMax();
            if(document.querySelector("body[stage='scan']")) {
                setStageTwo();

                let $node = null, canvas = Quagga.canvas.dom.image;

                $node = $('<div class="imgWrapper"><img /></div>');
                $node.find("img").attr("src", canvas.toDataURL());
                $("#picture-display").append($node);


                $.ajax({
                    url:"/scanner/checkForProduct?code=" +code,
                    type: "GET",
                    success: function (data) {
                        if (data.found === "true") {
                            let newIngredientName = document.getElementById("ingredient-name").cloneNode(true);
                            newIngredientName.removeAttribute("id");
                            newIngredientName.classList.add("hidden");
                            document.getElementById("scan-item-form").append(newIngredientName);
                            let ingredientName = document.getElementById("ingredient-name")
                            ingredientName.removeAttribute("name");
                            $(document).ready(function() {
                                $("#ingredient-name").select2();
                                $("#ingredient-name").val(data.ingredientId);
                                $("#ingredient-name").trigger('change')
                            });

                            const name = document.getElementById("product-name");
                            name.value = data.name;
                            name.readOnly = true;

                            const amount = document.getElementById("product-amount");
                            amount.value = data.amount;
                            amount.readOnly = true;

                            const productId = document.getElementById("product-id");
                            productId.value = data.productId;
                            productId.readOnly = true;

                        } else if(data.found === "false") {
                            const ingredientName = document.getElementById("ingredient-name");
                            $(document).ready(function() {
                                $("#ingredient-name").select2();
                            });
                            document.getElementById("create-product").setAttribute("checked", "")
                            document.getElementById("product-code").value = code;
                        }

                    },
                    error: function (data) {
                    }
                })
            }
            CodesMap.clear();
            App.currentNumberScans = 0;
        } else {
            App.currentNumberScans += 1;
        }
    });
});


function setStageTwo() {
    document.querySelector("body[stage='scan']").setAttribute("stage", "one");
    $("body").attr("stage", "one");
}

const itemscan_back = document.getElementById("product-scan-back");
const item_continue = document.getElementById("product-continue");
const add_single_item = document.getElementById("add-single-item");
const multiple_select = document.getElementById("unpackaged-product-select");

if (itemscan_back) {
    itemscan_back.onclick = function() {
        document.getElementsByTagName("body")[0].setAttribute("stage", "scan");
        document.getElementsByClassName("selected-picture")[0].classList.remove("hidden");
    }
}

if (multiple_select) {
    $(document).ready(function() {
        $("#unpackaged-product-select").select2();
    })
}

if (add_single_item) {
    add_single_item.onclick = function () {
        if (multiple_select.value !== "null") {
            setStageTwo();
            document.getElementsByClassName("selected-picture")[0].classList.add("hidden");
            $.ajax({
                url:"/scanner/checkForUnpackagedProduct?productId=" + multiple_select.value,
                type: "GET",
                success: function (data) {
                    if (data.found === "true") {
                        let newIngredientName = document.getElementById("ingredient-name").cloneNode(true);
                        newIngredientName.removeAttribute("id");
                        newIngredientName.classList.add("hidden");
                        document.getElementById("scan-item-form").append(newIngredientName);
                        let ingredientName = document.getElementById("ingredient-name")
                        ingredientName.removeAttribute("name");
                        $(document).ready(function() {
                            $("#ingredient-name").select2();
                            $("#ingredient-name").val(data.ingredientId);
                            $("#ingredient-name").trigger('change')
                        });

                        const name = document.getElementById("product-name");
                        name.value = data.name;
                        name.readOnly = true;

                        const amount = document.getElementById("product-amount");
                        const amountHeader = document.getElementById("product-amount-header");
                        // amount.classList.add("hidden");
                        // amountHeader.classList.add("hidden");
                        amount.value = data.amount;
                        amount.readOnly = true;

                        const productId = document.getElementById("product-id");
                        productId.value = data.productId;
                        productId.readOnly = true;

                    } else if(data.found === "false") {
                    }

                },
                error: function (data) {
                }
            })
        }
    }
}


if (item_continue) {
    item_continue.onclick = function() {
        document.getElementsByTagName("body")[0].setAttribute("stage", "two");
    }
}


let spaces = document.getElementById("scanner");
if (spaces) {
    spaces = spaces.getElementsByClassName("storage-space")
    Array.from(spaces).forEach(x => {
        x.querySelector("input").addEventListener("change", function () {
            Array.from(spaces).forEach(y => {
                y.setAttribute("selected", "false");
                if (y.querySelector("input").checked) {
                    y.setAttribute("selected", "true")
                }
            })
        })
    })
}

