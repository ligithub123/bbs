!function(a){a.fn.extend({page:function(b){return this.each(function(){var e,f,g,h,i,j,c=a(this).empty(),d={now:c.data("now")||1,max:c.data("max")||1,href:c.data("href")||"javascript:void(0)",target:c.data("target")||"_self",size:c.data("size")||"",first:c.data("first")||"&laquo;",last:c.data("last")||"&raquo;"};if(d=a.extend(d,b),d.max=Math.round(d.max),d.now=Math.round(d.now),!(d.max<=1||isNaN(d.max)||isNaN(d.now))){if(d.now=d.now<1?1:d.now>d.max?d.max:d.now,d.callback&&c.off("click").on("click","a",function(){var b=a(this);return b.parent().hasClass("disabled")||b.parent().hasClass("active")||d.callback.call(b,b.data("p")),!1}),e=a('<ul class="pagination"></ul>').addClass(d.size?"pagination-"+d.size:""),f=a('<li><a href="javascript:void(0)" target="'+d.target+'"></a></li>'),d.max<=10)for(g=1;g<=d.max;g++)h=f.clone(),h.find("a").data("p",g).html(g).attr("href",d.href.replace(/{[^}]*\}/,g)),g==d.now&&h.addClass("active"),e.append(h);else{for(d.now>=4&&(f.clone().find("a").data("p",1).html(d.first).attr("href",d.href.replace(/{[^}]*\}/,1)).parent().appendTo(e),f.clone().addClass("disabled").find("a").html("...").parent().appendTo(e)),i=d.max-d.now<2?d.max:d.now<4?5:d.now+2,j=i-4,g=j;i>=g;g++)h=f.clone(),g==d.now?h.addClass("active").find("a").html(g):h.find("a").data("p",g).html(g).attr("href",d.href.replace(/{[^}]*\}/,g)),e.append(h);d.now+3<=d.max&&(f.clone().addClass("disabled").find("a").html("...").parent().appendTo(e),f.clone().find("a").data("p",d.max).html(d.last).attr("href",d.href.replace(/{[^}]*\}/,d.max)).parent().appendTo(e))}c.append(e)}})}})}($);