(function() {
	var CustomizedForm =  function CustomizedForm(element1, options)
	{
		if(options == null)
		{
			throw "Parameter {} is null.";
		}
		else if(string.isNull(options.category))
		{
			throw "Parameter category is required."
		}
		this.element = $(element1);
		this.options = options;
		this.init();
	};
	CustomizedForm.prototype.sentCustomizedFormRequest = function(category, mappingType, anchor)
	{
		$.ajax({
			url: "/pms-portlet/actions/customizedFormMapping/getCustomziedFormMapping/",
			data : {category: category, mappingType : mappingType},
			dataType : "json",
			type : "post",
			success : function(response)
			{
				if(response && response.html)
				{
					this.htmlTempate = response.html;
					if(string.isNotNull(this.htmlTempate))
					{
						this.elementDisplayVal = this.element.css("display"); 
						var viewOnly = ('view' == this.options.mode);
						if(viewOnly)
						{
							this.element.css("display", "none");
						}
						this.element.append($(this.htmlTempate.trim()));
						if(this.options.key1)
						{
							this.sentCustomizedFormValusRequest(this.options.category, this.options.key1, this.options.key2, this.options.key3);
						}
						if(!viewOnly)
						{
							selectFieldsProcessor(this.element);
					        datetimeFieldsProcessor(this.element);	
					        switchFieldsProcessor(this.element);
					        emailFieldProcessor(this.element);
						}
					}
				}
				scrollBar.autoAdjustForTableAddOrRemoveRows();
			}.bind(this)
		});
	};
	CustomizedForm.prototype.sentCustomizedFormValusRequest = function(category, key1, key2, key3)
	{
		$.post("/pms-portlet/actions/customizedFormValues/getCustomizedFormValues",
			   {
					category : category,
					key1 : key1,
					key2 : key2,
					key3 : key3
			   },
			  function(data)
			   {
				   this.htmlData = data;
				   this.fillData();
			   }.bind(this),
   		      'json'
	   );
	};
	CustomizedForm.prototype.fillData = function()
	{
		 var viewOnly = ('view' == this.options.mode);
		   for(var key in this.htmlData)
		   {
			   this.element.find("*[name='"+key+"']").each(function(viewOnly, htmlData)
			   {
				   var $this = $(this);
				   if(viewOnly)
				   {
					   $this.parents("div.fieldUnit").find("span.required").remove();
					   if($this.parent().hasClass("date"))
					   {
						   this.parentNode.outerHTML = htmlData[key];
					   }
					   else
					   {
						   this.outerHTML = htmlData[key];
					   }  
				   }
				   else
				   {
					   if($this.attr("type") == 'checkbox')
					   {
						   if(htmlData[key])
						   {
							   $this.attr("checked", 'true');
						   }
					   }
					   else if($this.attr("type") == 'carNum')
					   {
						   $this.prevAll().remove();
					   }
					   else if(this.nodeName == 'TEXTAREA')
					   {
						   $this.css("resize", 'none');
					   }  
					   $this.attr("initValue", htmlData[key]);
					   $this.val(htmlData[key]);
				   }
			   }, [viewOnly, this.htmlData]);
		   }
		   if(viewOnly)
		   {
			   this.element.css("display", this.elementDisplayVal);
		   }
		   else
		   {
			   carNumberProcessor(this.element);
		   }
	};
	CustomizedForm.prototype.cleanUp = function()
	{
		this.element.html(this.htmlTempate.trim());
		if(this.options.key1)
		{
			this.fillData();
		}
	};
	CustomizedForm.prototype.refOnchange = function()
	{
		$("div.customizedForm-row").remove();
		this.materialType = this.ref.val();
		if(string.isNotNull(this.materialType))
		{
			this.sentCustomizedFormRequest(this.options.category, this.materialType, this.element);
		}
		else
		{
			scrollBar.autoAdjustForTableAddOrRemoveRows();
		}
	};
	CustomizedForm.prototype.formOnsubmit = function()
	{
		var customizedFormValus = {};
		var length = 0;
		$("*.customizedField").each(function(index){
			length = index + 1;
			$this = $(this);
			if($(this).attr("type") == 'checkbox')
			{
				customizedFormValus[$this.attr("name")] = $this.is(":checked");
			}
			else
			{
				customizedFormValus[$this.attr("name")] = $this.val();
			}
		});
		if(length > 0)
		{
			this.element.append("<input type='hidden' name='customizedFormValues' value='"+JSON.stringify(customizedFormValus)+"'></input>");
		}
	};
	CustomizedForm.prototype.init = function()
	{
		if(this.options.form)
		{
			this.form = $(this.options.form);
		}
		else
		{
			this.form = $(document.forms[0]);
		}
		this.form[0].customizedFormSubmit = this.formOnsubmit.bind(this); 
		this.form.submit(this.formOnsubmit.bind(this));
		if(this.options.ref)
		{
			this.ref = $(this.options.ref);
			this.ref.on("change", this.refOnchange.bind(this));
		}
		if(this.options.mappingType)
		{
			this.sentCustomizedFormRequest(this.options.category, this.options.mappingType, this.element);
		}
		this.element.append("<input type='hidden' name='customizedFormCategory' value='"+this.options.category+"'></input>");
		if(this.options.key1)
		{
			this.element.append("<input type='hidden' name='customizedFormValuesKey1' value='"+this.options.key1+"'></input>");
		}
		if(this.options.key2)
		{
			this.element.append("<input type='hidden' name='customizedFormValuesKey2' value='"+this.options.key2+"'></input>");
		}
		if(this.options.key3)
		{
			this.element.append("<input type='hidden' name='customizedFormValuesKey3' value='"+this.options.key3+"'></input>");
		}
		this.element[0].customizedFormCleanUp = function()
		{
			this.cleanUp();
		}.bind(this);
	}
	
	if (typeof jQuery !== "undefined" && jQuery !== null) 
	{
		jQuery.fn.customizedForm = function(options) 
		{
		  return this.each(function() 
		  {
		    return new CustomizedForm(this, options);
		  });
		};
	}
}).call(this);