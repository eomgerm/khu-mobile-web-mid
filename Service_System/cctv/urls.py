from django.urls import path

from cctv.views import AlertApiView, CctvApiView, CctvDetailApiView

urlpatterns = [
    path("api/cctvs/", CctvApiView.as_view()),
    path("api/cctvs/<int:cctv_id>/alerts/", AlertApiView.as_view()),
    path("api/cctvs/<int:cctv_id>/", CctvDetailApiView.as_view())
]
