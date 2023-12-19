from django.contrib import admin

from cctv.models import Cctv, Alert

# Register your models here.
admin.site.register(Cctv)
admin.site.register(Alert)
